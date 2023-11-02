package dev.steady.application.service;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.SurveyResult;
import dev.steady.application.domain.repository.ApplicationRepository;
import dev.steady.application.dto.request.SurveyResultRequest;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.global.auth.UserInfo;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;
    private final SteadyRepository steadyRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public CreateApplicationResponse createApplication(Long steadyId, List<SurveyResultRequest> request, UserInfo userInfo) {
        User user = userRepository.getUserBy(userInfo.userId());
        Steady steady = steadyRepository.getSteady(steadyId);

        Application application = new Application(user, steady);
        createSurveyResult(application, request);

        Application savedApplication = applicationRepository.save(application);
        return CreateApplicationResponse.from(savedApplication);
    }

    private List<SurveyResult> createSurveyResult(Application application, List<SurveyResultRequest> surveys) {
        return IntStream.range(0, surveys.size())
                .mapToObj(index -> SurveyResult.create(application,
                        surveys.get(index).question(),
                        surveys.get(index).answer(),
                        index))
                .toList();
    }

}
