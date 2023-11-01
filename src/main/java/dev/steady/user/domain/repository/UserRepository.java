package dev.steady.user.domain.repository;

import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User getUserBy(Long userId) {
        return findById(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    Boolean existsByNickname(String nickname);
    
}
