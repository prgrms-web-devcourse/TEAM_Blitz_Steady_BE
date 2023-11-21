package dev.steady.application.fixture;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.SurveyResult;
import dev.steady.application.dto.response.ApplicationDetailResponse;
import dev.steady.application.dto.response.ApplicationSummaryResponse;
import dev.steady.application.dto.response.SliceResponse;
import dev.steady.application.dto.response.SurveyResultResponse;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

import java.util.List;

public class ApplicationFixture {

    public static Application createApplication(User user, Steady steady) {
        Application application = new Application(user, steady);
        return application;
    }

    public static SliceResponse<ApplicationSummaryResponse> createApplicationSummaryResponse() {
        return new SliceResponse<>(
                List.of(new ApplicationSummaryResponse(1L, 10L, "닉네임", "profile.url"),
                        new ApplicationSummaryResponse(2L, 11L, "닉네임2", "profile.url"),
                        new ApplicationSummaryResponse(3L, 12L, "닉네임3", "profile.url")),
                3,
                false);
    }

    public static ApplicationDetailResponse createApplicationDetailResponse() {
        return new ApplicationDetailResponse(
                List.of(
                        new SurveyResultResponse("질문", "답변"),
                        new SurveyResultResponse("질문1", "답변1"),
                        new SurveyResultResponse("질문3", "답변2")
                )
        );
    }

    public static List<SurveyResult> createSurveyResults(Application application) {
        return List.of(SurveyResult.create(application, "질문1", "답변1", 0),
                SurveyResult.create(application, "질문2", "답변2", 1),
                SurveyResult.create(application, "질문3", "답변3", 2)
        );
    }

}
