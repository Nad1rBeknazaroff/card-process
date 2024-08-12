package smartcast.uz.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.Purpose;

import java.util.UUID;

@Data
public class WithdrawFundRequest {

    @NotNull(message = "EXTERNAL_ID_ERROR")
    private UUID externalId;

    @Positive(message = "WITHDRAW_AMOUNT_ERROR")
    private long amount;

    @NotNull(message = "PURPOSE_ERROR")
    private Purpose purpose;
    private Currency currency = Currency.UZS;

}
