package dev.steady.user.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.user.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.steady.user.exception.StackErrorCode.STACK_NOT_FOUND;

public interface StackRepository extends JpaRepository<Stack, Long> {

    default Stack getById(Long stackId) {
        return findById(stackId)
                .orElseThrow(() -> new NotFoundException(STACK_NOT_FOUND));
    }

}
