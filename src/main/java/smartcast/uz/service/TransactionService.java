package smartcast.uz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartcast.uz.domain.Card;
import smartcast.uz.domain.Transaction;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.TransactionType;
import smartcast.uz.payload.request.TopUpRequest;
import smartcast.uz.payload.request.WithdrawFundRequest;
import smartcast.uz.payload.response.TransactionResponse;

import java.util.UUID;

public interface TransactionService {

    Transaction createWithdrawTransaction(Card card, WithdrawFundRequest request);

    Transaction createTopUpTransaction(Card card, TopUpRequest request);

    Page<TransactionResponse> getTransactionHistory(UUID cardId, TransactionType type, UUID transactionId, UUID externalId, Currency currency, Pageable pageable);
}
