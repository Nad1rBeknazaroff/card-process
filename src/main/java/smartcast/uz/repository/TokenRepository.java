package smartcast.uz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartcast.uz.domain.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<Token, Long> {

    @Query("SELECT t FROM Token t JOIN t.user u WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")

    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
