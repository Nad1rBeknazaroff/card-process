package smartcast.uz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smartcast.uz.enums.ApiEndpoint;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.TransactionType;
import smartcast.uz.payload.request.CardCreateRequest;
import smartcast.uz.payload.request.TopUpRequest;
import smartcast.uz.payload.request.WithdrawFundRequest;
import smartcast.uz.service.CardService;
import smartcast.uz.service.TransactionService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
@Validated
public class CardController {
    private final CardService cardService;
    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<?> createCard(@RequestHeader("Idempotency-Key") UUID idempotencyKey,
                                        @Valid @RequestBody CardCreateRequest request) {
        var response = cardService.createCard(request, idempotencyKey, ApiEndpoint.CREATE_CARD);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @GetMapping("{cardId}")
    public ResponseEntity<?> getCard(@PathVariable UUID cardId) {
        var response = cardService.getCard(cardId);
        return ResponseEntity.status(response.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .eTag(String.valueOf(response.getHeader()))
                .body(response.getResponse());
    }

    @PostMapping("{cardId}/block")
    public ResponseEntity<?> blockCard(@RequestHeader("If-Match") UUID ifMatch,
                                       @PathVariable UUID cardId) {
        return ResponseEntity.status(cardService.blockCard(cardId, ifMatch).getHttpStatus()).build();
    }

    @PostMapping("{cardId}/unblock")
    public ResponseEntity<?> unblockCard(@RequestHeader("If-Match") UUID ifMatch,
                                         @PathVariable UUID cardId) {
        return ResponseEntity.status(cardService.unblockCard(cardId, ifMatch).getHttpStatus()).build();
    }

    @PostMapping("{cardId}/debit")
    public ResponseEntity<?> withdraw(@RequestHeader("Idempotency-Key") UUID idempotencyKey,
                                      @PathVariable UUID cardId,
                                      @Valid @RequestBody WithdrawFundRequest request) {
        var response = cardService.withdraw(cardId, request, idempotencyKey, ApiEndpoint.WITHDRAW_FUNDS);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PostMapping("{cardId}/credit")
    public ResponseEntity<?> topUp(@RequestHeader("Idempotency-Key") UUID idempotencyKey,
                                   @PathVariable UUID cardId,
                                   @Valid @RequestBody TopUpRequest request) {
        var response = cardService.topUp(cardId, request, idempotencyKey, ApiEndpoint.TOP_UP);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @GetMapping("{cardId}/transactions")
    public ResponseEntity<?> getTransactionHistory(@PathVariable UUID cardId,
                                                   @RequestParam(required = false) TransactionType type,
                                                   @RequestParam(required = false) UUID transactionId,
                                                   @RequestParam(required = false) UUID externalId,
                                                   @RequestParam(required = false) Currency currency,
                                                   Pageable pageable) {


        var response = transactionService.getTransactionHistory(cardId,
                type, transactionId, externalId, currency, pageable);
        return ResponseEntity.ok(response);
    }
}
