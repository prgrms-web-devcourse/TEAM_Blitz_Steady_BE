package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Promotion;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyQuestion;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.User;
import dev.steady.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SteadyService {


    private final SteadyRepository steadyRepository;
    private final UserRepository userRepository;
    private final SteadyQuestionRepository steadyQuestionRepository;
    private final AuthContext authContext;

    @Transactional
    public Long create(SteadyCreateRequest request) {
        Promotion promotion = new Promotion();
        Steady steady = request.toEntity(promotion);

        Long userId = authContext.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        createSteadyLeader(steady, user);
        Steady savedSteady = steadyRepository.save(steady);

        List<SteadyQuestion> steadyQuestions = createSteadyQuestions(request.questionList(), savedSteady);
        steadyQuestionRepository.saveAll(steadyQuestions);
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

    private List<SteadyQuestion> createSteadyQuestions(List<String> questionList, Steady steady) {
        List<SteadyQuestion> steadyQuestions = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            SteadyQuestion steadyQuestion = SteadyQuestion.builder()
                    .content(questionList.get(i))
                    .order(i + 1)
                    .steady(steady)
                    .build();
            steadyQuestions.add(steadyQuestion);
        }
        return steadyQuestions;
    }

}
