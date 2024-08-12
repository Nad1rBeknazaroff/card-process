package smartcast.uz.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"key", "api_endpoint"})
})
@Getter
@Setter
@RequiredArgsConstructor
public class IdempotencyKey extends BaseEntity<UUID> {

    @Column(nullable = false)
    private UUID key;

    @Column(nullable = false)
    private String apiEndpoint;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String responsePayload;
}

