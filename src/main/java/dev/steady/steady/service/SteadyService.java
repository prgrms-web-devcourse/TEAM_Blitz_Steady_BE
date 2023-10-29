package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.domain.*;
import dev.steady.steady.domain.repository.SteadyPositionRepository;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.dto.response.PageResponse;
import dev.steady.steady.dto.response.SteadySearchResponse;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final SteadyQuestionRepository steadyQuestionRepository;
    private final SteadyPositionRepository steadyPositionRepository;

    @Transactional
    public Long create(SteadyCreateRequest request, AuthContext authContext) {
        Long userId = authContext.getUserId();
        User user = userRepository.getUserBy(userId);
        List<SteadyStack> steadyStacks = createSteadyStacks(request.stacks());
        Promotion promotion = new Promotion();
        Steady steady = request.toEntity(user, promotion, steadyStacks);
        Steady savedSteady = steadyRepository.save(steady);

        List<SteadyQuestion> steadyQuestions = createSteadyQuestions(request.questions(), savedSteady);
        steadyQuestionRepository.saveAll(steadyQuestions);

        List<SteadyPosition> steadyPositions = createSteadyPositions(request.positions(), savedSteady);
        steadyPositionRepository.saveAll(steadyPositions);

        return savedSteady.getId();
    }

    public PageResponse<SteadySearchResponse> getSteadies(SteadyPageRequest request) {
        Page<Steady> steadies = steadyRepository.findAll(request.toPageable());
        Page<SteadySearchResponse> searchResponses = steadies.map(SteadySearchResponse::from);
        return PageResponse.from(searchResponses);
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

    private List<SteadyPosition> createSteadyPositions(List<Long> positions, Steady steady) {
        return IntStream.range(0, positions.size())
                .mapToObj(index -> {
                    Position position = positionRepository.findById(positions.get(index))
                            .orElseThrow(IllegalArgumentException::new);
                    return SteadyPosition.builder()
                            .position(position)
                            .steady(steady)
                            .build();
                }).toList();
    }

    private List<SteadyStack> createSteadyStacks(List<Long> stacks) {
        return IntStream.range(0, stacks.size())
                .mapToObj(index -> {
                    Stack stack = stackRepository.findById(stacks.get(index))
                            .orElseThrow(IllegalArgumentException::new);;
                    return SteadyStack.builder()
                            .stack(stack)
                            .build();
                }).toList();
    }

}
