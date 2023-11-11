package dev.steady.user.service;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.dto.response.PositionResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public PositionResponses getPositions() {
        List<Position> positions = positionRepository.findAll();
        return PositionResponses.from(positions);
    }

}
