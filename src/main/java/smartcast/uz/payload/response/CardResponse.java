package smartcast.uz.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartcast.uz.enums.CardStatus;
import smartcast.uz.enums.Currency;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse implements Serializable {
    private String pan;
    private UUID cardId;
    private Long userId;
    private CardStatus status;
    private Long balance;
    private Currency currency;
}
