package smartcast.uz.mapper;

import org.springframework.stereotype.Component;
import smartcast.uz.domain.Card;
import smartcast.uz.domain.User;
import smartcast.uz.payload.request.CardCreateRequest;
import smartcast.uz.payload.response.CardResponse;
import smartcast.uz.utils.ApplicationUtils;

@Component
public class CardMapper {

    public Card toEntity(CardCreateRequest request, User user) {
        return Card.builder()
                .pan(request.getPan())
                .balance(request.getInitialAmount())
                .currency(request.getCurrency())
                .status(request.getStatus())
                .user(user).build();

    }

    public CardResponse toCardResponse(Card card) {
        return CardResponse.builder()
                .pan(ApplicationUtils.maskedPan(card.getPan()))
                .cardId(card.getId())
                .userId(card.getUser().getId())
                .status(card.getStatus())
                .balance(card.getBalance())
                .currency(card.getCurrency()).build();
    }
}
