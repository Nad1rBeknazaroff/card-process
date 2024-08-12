package smartcast.uz.utils;

import smartcast.uz.domain.Card;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ETagGenerator {

    public static UUID generateETag(Card card) {
        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append(card.getId())
                .append('|')
                .append(card.getPan())
                .append('|')
                .append(card.getUser().getId())
                .append('|')
                .append(card.getModifiedDate().getTime())
                .append('|')
                .append(card.getStatus().name())
                .append('|')
                .append(card.getCurrency());

        UUID eTagUUID = UUID.nameUUIDFromBytes(inputBuilder.toString().getBytes(StandardCharsets.UTF_8));
        return eTagUUID;
    }
}

