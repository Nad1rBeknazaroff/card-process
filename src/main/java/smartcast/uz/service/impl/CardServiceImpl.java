package smartcast.uz.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smartcast.uz.domain.Card;
import smartcast.uz.enums.ApiEndpoint;
import smartcast.uz.enums.CardStatus;
import smartcast.uz.enums.Currency;
import smartcast.uz.exception.*;
import smartcast.uz.mapper.CardMapper;
import smartcast.uz.mapper.TransactionMapper;
import smartcast.uz.payload.request.CardCreateRequest;
import smartcast.uz.payload.request.TopUpRequest;
import smartcast.uz.payload.request.WithdrawFundRequest;
import smartcast.uz.payload.response.ApiResponse;
import smartcast.uz.payload.response.CardResponse;
import smartcast.uz.payload.response.TransactionResponse;
import smartcast.uz.repository.CardRepository;
import smartcast.uz.service.CardService;
import smartcast.uz.service.IdempotencyService;
import smartcast.uz.service.TransactionService;
import smartcast.uz.service.UserService;
import smartcast.uz.utils.ApplicationUtils;
import smartcast.uz.utils.ETagGenerator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final IdempotencyService idempotencyService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    @SneakyThrows
    @Override
    @Cacheable(value = "create-card", key = "#idempotencyKey")
    public ApiResponse<?> createCard(CardCreateRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint) {
        return processIdempotency(idempotencyKey, apiEndpoint, request, HttpStatus.CREATED, new TypeReference<CardResponse>() {
        }, () -> {
            var userId = ApplicationUtils.getUserId();
            if (cardRepository.countByUserIdAndStatusAndDeletedFalse(userId, CardStatus.ACTIVE) >= 3) {
                throw new CardLimitExceededException();
            }

            var user = userService.getUser(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId.toString()));

            var savedCard = cardRepository.save(cardMapper.toEntity(request, user));
            return cardMapper.toCardResponse(savedCard);
        });
    }

    @Override
    public ApiResponse<?> getCard(UUID cardId) {
        Card card = findCardById(cardId);
        UUID eTag = ETagGenerator.generateETag(card);

        return ApiResponse.<CardResponse>builder()
                .httpStatus(HttpStatus.OK)
                .response(cardMapper.toCardResponse(card))
                .header(eTag)
                .build();
    }

    @Override
    public ApiResponse<?> blockCard(UUID cardId, UUID ifMatch) {
        Card card = findCardById(cardId);
        validateETag(card, ifMatch);

        if (card.getStatus() != CardStatus.ACTIVE) throw new CardNotActiveException(cardId.toString());
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return ApiResponse.<Void>builder().httpStatus(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ApiResponse<?> unblockCard(UUID cardId, UUID ifMatch) {

        Card card = findCardById(cardId);
        validateETag(card, ifMatch);

        if (card.getStatus() != CardStatus.BLOCKED) throw new CardNotBlockedException(cardId.toString());
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
        return ApiResponse.<Void>builder().httpStatus(HttpStatus.NO_CONTENT).build();
    }


    @SneakyThrows
    @Transactional
    @Cacheable(value = "withdraw", key = "#idempotencyKey")
    @Override
    public ApiResponse<?> withdraw(UUID cardId, WithdrawFundRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint) {
        return processIdempotency(idempotencyKey, apiEndpoint, request, HttpStatus.OK, new TypeReference<TransactionResponse>() {
        }, () -> {
            Card card = findCardById(cardId);
            checkCardStatusIsActive(card);
            long withdrawalAmount = convertAmount(request.getAmount(), request.getCurrency(), card.getCurrency());
            if (card.getBalance() < withdrawalAmount)
                throw new InsufficientFundsException();
            card.setBalance(card.getBalance() - withdrawalAmount);
            card = cardRepository.save(card);
            var transaction = transactionService.createWithdrawTransaction(card, request);
            return transactionMapper.toTransactionResponse(transaction, false);
        });

    }

    @SneakyThrows
    @Transactional
    @Cacheable(value = "top-up", key = "#idempotencyKey")
    @Override
    public ApiResponse<?> topUp(UUID cardId, TopUpRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint) {
        return processIdempotency(idempotencyKey, apiEndpoint, request, HttpStatus.OK, new TypeReference<TransactionResponse>() {
        }, () -> {
            Card card = findCardById(cardId);
            checkCardStatusIsActive(card);
            long amount = convertAmount(request.getAmount(), request.getCurrency(), card.getCurrency());
            card.setBalance(card.getBalance() + amount);
            card = cardRepository.save(card);
            var transaction = transactionService.createTopUpTransaction(card, request);
            return transactionMapper.toTransactionResponse(transaction, false);
        });
    }

    @SneakyThrows
    private <T, R> ApiResponse<R> processIdempotency(UUID idempotencyKey, ApiEndpoint apiEndpoint, T request, HttpStatus httpStatus, TypeReference<R> responseType, ApiLogic<R> logic) {
        var idempotency = idempotencyService.checkIdempotency(idempotencyKey, apiEndpoint.getUrl());
        if (idempotency.isPresent()) {
            R cachedResponse = objectMapper.readValue(idempotency.get().getResponsePayload(), responseType);
            return ApiResponse.<R>builder()
                    .response(cachedResponse)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        R response = logic.execute();
        idempotencyService.saveIdempotencyKey(idempotencyKey, apiEndpoint.getUrl(),
                objectMapper.writeValueAsString(request),
                objectMapper.writeValueAsString(response));

        return ApiResponse.<R>builder()
                .response(response)
                .httpStatus(httpStatus)
                .build();
    }

    @FunctionalInterface
    private interface ApiLogic<R> {
        R execute() throws Exception;
    }

    private long convertAmount(long amount, Currency requestCurrency, Currency cardCurrency) {
        if (requestCurrency == Currency.USD && cardCurrency == Currency.UZS) {
            return amount * Currency.USD.getExchangeRate();
        } else if (requestCurrency == Currency.UZS && cardCurrency == Currency.USD) {
            return amount / Currency.USD.getExchangeRate();
        } else return amount;
    }

    private Card findCardById(UUID cardId) {
        return cardRepository.findByIdAndUserIdAndDeletedFalse(cardId, ApplicationUtils.getUserId())
                .orElseThrow(() -> new CardNotFoundException(cardId.toString()));
    }

    private void checkCardStatusIsActive(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) throw new CardNotActiveException(card.getId().toString());
    }

    private void validateETag(Card card, UUID ifMatch) {
        UUID eTag = ETagGenerator.generateETag(card);
        if (!eTag.equals(ifMatch)) throw new ETagMismatchException();
    }
}
