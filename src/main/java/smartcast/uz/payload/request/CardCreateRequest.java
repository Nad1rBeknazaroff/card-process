package smartcast.uz.payload.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import smartcast.uz.enums.CardStatus;
import smartcast.uz.enums.Currency;

@Data
public class CardCreateRequest {

    @Digits(integer = 16, fraction = 0, message = "PAN_LENGTH_AND_DIGIT_ERROR")
    @Size(min = 16, max = 16, message = "PAN_LENGTH_AND_DIGIT_ERROR")
    private String pan;
    private CardStatus status = CardStatus.ACTIVE;
    @PositiveOrZero(message = "CARD_INITIAL_AMOUNT_ERROR")
    @Max(value = 10000, message = "CARD_INITIAL_AMOUNT_ERROR")
    private long initialAmount = 0L;
    private Currency currency = Currency.UZS;
}
