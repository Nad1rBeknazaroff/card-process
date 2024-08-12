package smartcast.uz.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartcast.uz.domain.Card;
import smartcast.uz.domain.Transaction;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.Purpose;
import smartcast.uz.enums.TransactionType;
import smartcast.uz.exception.CardNotFoundException;
import smartcast.uz.mapper.TransactionMapper;
import smartcast.uz.payload.request.TopUpRequest;
import smartcast.uz.payload.request.WithdrawFundRequest;
import smartcast.uz.payload.response.TransactionResponse;
import smartcast.uz.repository.CardRepository;
import smartcast.uz.repository.TransactionRepository;
import smartcast.uz.service.TransactionService;
import smartcast.uz.utils.ApplicationUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public Transaction createWithdrawTransaction(Card card, WithdrawFundRequest request) {
        return saveTransaction(card, request.getAmount(), TransactionType.DEBIT, request.getPurpose(), request.getCurrency(), request.getExternalId());
    }

    @Transactional
    @Override
    public Transaction createTopUpTransaction(Card card, TopUpRequest request) {
        return saveTransaction(card, request.getAmount(), TransactionType.CREDIT, null, request.getCurrency(), request.getExternalId());
    }

    public Page<TransactionResponse> getTransactionHistory(UUID cardId,
                                                           TransactionType type,
                                                           UUID transactionId,
                                                           UUID externalId,
                                                           Currency currency,
                                                           Pageable pageable
    ) {
        Card card = cardRepository.findByIdAndUserIdAndDeletedFalse(cardId, ApplicationUtils.getUserId())
                .orElseThrow(() -> new CardNotFoundException(cardId.toString()));
        Page<Transaction> transactionsPage = transactionRepository.getTransactionsByFilter(
                cardId, type, transactionId, externalId, currency, pageable);
        List<TransactionResponse> transactionResponses = transactionsPage.getContent().stream()
                .map(transaction -> transactionMapper.toTransactionResponse(transaction, true))
                .toList();
        return new PageImpl<>(transactionResponses, pageable, transactionsPage.getTotalElements());
    }

    private Transaction saveTransaction(Card card, long amount, TransactionType type, Purpose purpose, Currency currency, UUID externalId) {
        return transactionRepository.save(Transaction.builder()
                .amount(amount)
                .purpose(purpose)
                .currency(currency)
                .externalId(externalId)
                .exchangeRate(currency.getExchangeRate())
                .card(card)
                .type(type)
                .build());
    }
}


