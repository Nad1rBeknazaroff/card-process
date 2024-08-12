package smartcast.uz.service;

import smartcast.uz.enums.ApiEndpoint;
import smartcast.uz.payload.request.CardCreateRequest;
import smartcast.uz.payload.request.TopUpRequest;
import smartcast.uz.payload.request.WithdrawFundRequest;
import smartcast.uz.payload.response.ApiResponse;

import java.util.UUID;

public interface CardService {

    ApiResponse<?> createCard(CardCreateRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint);
    ApiResponse<?> getCard(UUID cardId);
    ApiResponse<?> blockCard(UUID cardId, UUID ifMatch);
    ApiResponse<?> unblockCard(UUID cardId, UUID ifMatch);
    ApiResponse<?> withdraw(UUID cardId, WithdrawFundRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint);
    ApiResponse<?> topUp(UUID cardId, TopUpRequest request, UUID idempotencyKey, ApiEndpoint apiEndpoint);
}
