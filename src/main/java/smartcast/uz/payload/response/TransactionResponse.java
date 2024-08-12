package smartcast.uz.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.Purpose;
import smartcast.uz.enums.TransactionType;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse implements Serializable {
    private UUID transactionId;
    private UUID externalId;
    private UUID cardId;
    private long afterBalance;
    private long amount;
    private TransactionType type;
    private Purpose purpose;
    private Currency currency;
    @JsonInclude
    private Long exchangeRate;
}
