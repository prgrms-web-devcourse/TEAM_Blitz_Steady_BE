package dev.steady.steady.infrastructure;

import dev.steady.global.config.JpaConfig;
import dev.steady.global.config.QueryDslConfig;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.SteadyPositionRepository;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.dto.SearchKeywordDto;
import dev.steady.steady.dto.request.SteadySearchRequest;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static dev.steady.steady.fixture.SteadyFixtures.*;
import static dev.steady.user.fixture.UserFixtures.*;

@DataJpaTest
@Import({JpaConfig.class, QueryDslConfig.class})
class SteadyRepositoryCustomImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SteadyRepositoryCustomImpl queryDslRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private SteadyRepository steadyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private SteadyPositionRepository steadyPositionRepository;

    @Autowired
    private SteadyQuestionRepository steadyQuestionRepository;


    @Test
    void test() {
        var position = createPosition();
        positionRepository.save(position);
        var user = createFirstUser(position);
        userRepository.save(user);
        var stack = createStack();
        stackRepository.save(stack);

        var steadyRequest = createSteadyRequest(stack.getId(), position.getId());
        Steady steady = steadyRequest.toEntity(user, List.of(stack));
        steadyRepository.save(steady);

        var steadyPosition = createSteadyPosition(steady, position);
        steadyPositionRepository.save(steadyPosition);

        var steadyQuestion = createSteadyQuestion(steady, steadyRequest.questions());
        steadyQuestionRepository.saveAll(steadyQuestion);

        SteadySearchRequest request = new SteadySearchRequest(0, "DESC", null, steady.getSteadyMode().toString(), position.getName(), "false");
        Pageable pageable = request.toPageable();
        SearchKeywordDto of = SearchKeywordDto.of(request);

        queryDslRepository.findBySearchRequest(of, pageable);
    }

}