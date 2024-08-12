package smartcast.uz.repository;

import org.springframework.stereotype.Repository;
import smartcast.uz.domain.Card;
import smartcast.uz.enums.CardStatus;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends BaseRepository<Card, UUID>{
    Long countByUserIdAndStatusAndDeletedFalse(long userId, CardStatus status);

    Optional<Card> findByIdAndUserIdAndDeletedFalse(UUID id, Long userId);
}
