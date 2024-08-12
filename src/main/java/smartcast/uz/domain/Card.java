package smartcast.uz.domain;


import jakarta.persistence.*;
import lombok.*;
import smartcast.uz.enums.CardStatus;
import smartcast.uz.enums.Currency;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity<UUID> {

    @Column(length = 16, nullable = false)
    private String pan;

    @Builder.Default
    private long balance = 0L;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @ManyToOne
    private User user;
}
