package dev.steady.review.domain.repository;

import dev.steady.review.domain.UserCard;
import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

    List<UserCard> findAllByUser(User user);

}
