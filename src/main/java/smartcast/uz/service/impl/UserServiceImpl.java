package smartcast.uz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartcast.uz.domain.User;
import smartcast.uz.repository.UserRepository;
import smartcast.uz.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(long id) {
        return userRepository.findByIdAndDeletedFalse(id);
    }
}
