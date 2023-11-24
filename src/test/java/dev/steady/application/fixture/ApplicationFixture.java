package dev.steady.application.fixture;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.SurveyResult;
import dev.steady.application.dto.response.ApplicationDetailResponse;
import dev.steady.application.dto.response.ApplicationSummaryResponse;
import dev.steady.application.dto.response.MyApplicationSummaryResponse;
import dev.steady.application.dto.response.SliceResponse;
import dev.steady.application.dto.response.SurveyResultResponse;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static dev.steady.application.domain.ApplicationStatus.ACCEPTED;
import static dev.steady.application.domain.ApplicationStatus.REJECTED;
import static dev.steady.application.domain.ApplicationStatus.WAITING;

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

    public static SliceResponse<MyApplicationSummaryResponse> createMyApplicationSummaryResponse() {
        return new SliceResponse<>(
                List.of(
                        new MyApplicationSummaryResponse(1L, 1L,"스테디 제목1", LocalDateTime.of(2023, 12, 31, 12, 30), WAITING),
                        new MyApplicationSummaryResponse(2L, 2L,"스테디 제목2", LocalDateTime.of(2023, 12, 31, 12, 30), REJECTED),
                        new MyApplicationSummaryResponse(3L, 3L,"스테디 제목3", LocalDateTime.of(2023, 12, 31, 12, 30), ACCEPTED)),
                3,
                false
        );
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
