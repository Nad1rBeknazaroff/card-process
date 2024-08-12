package smartcast.uz.repository;


import org.springframework.stereotype.Repository;
import smartcast.uz.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsernameAndDeletedFalse(String username);

    Optional<User> findByUsername(String username);
}
