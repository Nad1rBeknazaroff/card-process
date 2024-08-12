package smartcast.uz.domain;

import jakarta.persistence.*;
import lombok.*;
import smartcast.uz.enums.Currency;
import smartcast.uz.enums.Purpose;
import smartcast.uz.enums.TransactionType;

import java.util.UUID;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity<UUID> {
    @ManyToOne
    private Card card;
    private UUID externalId;
    private long amount;

    @Builder.Default
    @Column(length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.UZS;
    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    @Column(length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private long exchangeRate;
}
