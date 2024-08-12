package smartcast.uz.domain;

import jakarta.persistence.*;
import lombok.*;
import smartcast.uz.enums.TokenType;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String token;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
