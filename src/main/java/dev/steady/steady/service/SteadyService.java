package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.domain.*;
import dev.steady.steady.domain.repository.SteadyPositionRepository;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.domain.repository.SteadyStackRepository;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SteadyService {

    private final UserRepository userRepository;
    private final StackRepository stackRepository;
    private final SteadyRepository steadyRepository;
    private final PositionRepository positionRepository;
    private final SteadyStackRepository steadyStackRepository;
    private final SteadyQuestionRepository steadyQuestionRepository;
    private final SteadyPositionRepository steadyPositionRepository;

    @Transactional
    public Long create(SteadyCreateRequest request, AuthContext authContext) {
        Promotion promotion = new Promotion();
        Steady steady = request.toEntity(promotion);

        Long userId = authContext.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        createSteadyLeader(steady, user);
        Steady savedSteady = steadyRepository.save(steady);

        List<SteadyQuestion> steadyQuestions = createSteadyQuestions(request.questions(), savedSteady);
        steadyQuestionRepository.saveAll(steadyQuestions);

        List<SteadyPosition> steadyPositions = createSteadyPositions(request.positions(), savedSteady);
        steadyPositionRepository.saveAll(steadyPositions);

        List<SteadyStack> steadyStacks = createSteadyStacks(request.stacks(), savedSteady);
        steadyStackRepository.saveAll(steadyStacks);
        return savedSteady.getId();
    }

    private void createSteadyLeader(Steady steady, User user) {
        Participant participant = Participant.builder()
                .user(user)
                .steady(steady)
                .isLeader(true)
                .build();
        steady.addParticipant(participant);
    }

    private List<SteadyQuestion> createSteadyQuestions(List<String> questions, Steady steady) {
        return IntStream.range(0, questions.size())
                .mapToObj(index -> SteadyQuestion.builder()
                        .content(questions.get(index))
                        .sequence(index + 1)
                        .steady(steady)
                        .build())
                .toList();
    }

    private List<SteadyPosition> createSteadyPositions(List<String> positions, Steady steady) {
        return IntStream.range(0, positions.size())
                .mapToObj(index -> {
                    Position position = positionRepository.getPositionByName(positions.get(index));
                    return SteadyPosition.builder()
                            .position(position)
                            .steady(steady)
                            .build();
                }).toList();
    }

    private List<SteadyStack> createSteadyStacks(List<String> stacks, Steady steady) {
        return IntStream.range(0, stacks.size())
                .mapToObj(index -> {
                    Stack stack = stackRepository.getStackByName(stacks.get(index));
                    return SteadyStack.builder()
                            .stack(stack)
                            .steady(steady)
                            .build();
                }).toList();
    }

}
