package smartcast.uz.repository;

import org.springframework.stereotype.Repository;
import smartcast.uz.domain.IdempotencyKey;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdempotencyRepository extends BaseRepository<IdempotencyKey, UUID> {
    Optional<IdempotencyKey> findByKeyAndAndApiEndpointAndDeletedFalse(UUID key, String apiEndpoint);
}
