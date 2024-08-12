package smartcast.uz.service;

import smartcast.uz.domain.IdempotencyKey;

import java.util.Optional;
import java.util.UUID;

public interface IdempotencyService {
    Optional<IdempotencyKey> checkIdempotency(UUID key, String apiEndpoint);

    IdempotencyKey saveIdempotencyKey(UUID key, String apiEndpoint, String requestPayload, String responsePayload);
}

