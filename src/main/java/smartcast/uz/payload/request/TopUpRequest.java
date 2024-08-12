package smartcast.uz.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import smartcast.uz.enums.Currency;

import java.util.UUID;

@Data
public class TopUpRequest {

    @NotNull(message = "EXTERNAL_ID_ERROR")
    private UUID externalId;

    @Positive(message = "WITHDRAW_AMOUNT_ERROR")
    private long amount;
    private Currency currency = Currency.UZS;
}
