package smartcast.uz.service;

import smartcast.uz.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(long id);
}
