package smartcast.uz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartcast.uz.domain.IdempotencyKey;
import smartcast.uz.repository.IdempotencyRepository;
import smartcast.uz.service.IdempotencyService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdempotencyServiceImpl implements IdempotencyService {

    private final IdempotencyRepository idempotencyKeyRepository;

    @Override
    public Optional<IdempotencyKey> checkIdempotency(UUID key, String apiEndpoint) {
        return idempotencyKeyRepository.findByKeyAndAndApiEndpointAndDeletedFalse(key, apiEndpoint);
    }

    @Override
    public IdempotencyKey saveIdempotencyKey(UUID key, String apiEndpoint, String requestPayload, String responsePayload) {
        IdempotencyKey idempotencyKey = new IdempotencyKey();
        idempotencyKey.setKey(key);
        idempotencyKey.setApiEndpoint(apiEndpoint);
        idempotencyKey.setRequestPayload(requestPayload);
        idempotencyKey.setResponsePayload(responsePayload);
        return idempotencyKeyRepository.save(idempotencyKey);
    }
}
