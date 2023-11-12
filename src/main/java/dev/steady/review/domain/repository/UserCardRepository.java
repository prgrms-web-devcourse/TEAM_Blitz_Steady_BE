package dev.steady.review.domain.repository;

import dev.steady.review.domain.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
}
