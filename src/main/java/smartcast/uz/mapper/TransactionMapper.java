package smartcast.uz.mapper;

import org.springframework.stereotype.Component;
import smartcast.uz.domain.Transaction;
import smartcast.uz.payload.response.TransactionResponse;

@Component
public class TransactionMapper {
    public TransactionResponse toTransactionResponse(Transaction transaction, boolean isTypeRequired) {
        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .externalId(transaction.getExternalId())
                .cardId(transaction.getCard().getId())
                .afterBalance(transaction.getCard().getBalance())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .purpose(transaction.getPurpose())
                .exchangeRate(transaction.getExchangeRate())
                .type(isTypeRequired ? transaction.getType() : null)
                .build();
    }

}
