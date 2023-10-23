package dev.steady.user.infrastructure;

import dev.steady.user.domain.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStackRepository extends JpaRepository<UserStack, Long> {

    List<UserStack> findAllByUserId(Long userId);

}
