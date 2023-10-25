package dev.steady.user.infrastructure;

import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User getUserBy(Long userId) {
        return findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());
    }

}
