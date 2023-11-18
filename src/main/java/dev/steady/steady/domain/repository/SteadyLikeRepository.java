package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyLike;
import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SteadyLikeRepository extends JpaRepository<SteadyLike, Long> {

    int countBySteady(Steady steady);

    Optional<SteadyLike> findByUserAndSteady(User user, Steady steady);

}
