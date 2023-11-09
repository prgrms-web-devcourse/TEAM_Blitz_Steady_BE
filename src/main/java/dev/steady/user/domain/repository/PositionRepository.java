package dev.steady.user.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.user.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.steady.user.exception.PositionErrorCode.POSITION_NOT_FOUND;

public interface PositionRepository extends JpaRepository<Position, Long> {

    default Position getById(Long positionId) {
        return findById(positionId)
                .orElseThrow(() -> new NotFoundException(POSITION_NOT_FOUND));
    }

}
