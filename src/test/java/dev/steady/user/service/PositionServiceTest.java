package dev.steady.user.service;

import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.dto.response.PositionsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.steady.user.fixture.UserFixtures.createAnotherPosition;
import static dev.steady.user.fixture.UserFixtures.createPosition;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionRepository positionRepository;

    @Test
    @DisplayName("모든 포지션을 가져올 수 있다.")
    void getPositionsTest() {
        // given
        positionRepository.save(createPosition());
        positionRepository.save(createAnotherPosition());

        // when
        PositionsResponse response = positionService.getPositions();

        // then
        int expectedSize = 2;
        assertThat(response.positions().size()).isEqualTo(expectedSize);
    }

}
