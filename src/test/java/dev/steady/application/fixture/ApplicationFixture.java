package dev.steady.application.fixture;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.SurveyResult;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

import java.util.List;

public class ApplicationFixture {

    public static Application createApplication(User user, Steady steady) {
        Application application = new Application(user, steady);
        saveSurveyResults(application);
        return application;
    }

    private static void saveSurveyResults(Application application) {
        List.of(SurveyResult.create(application, "질문1", "질문1", 0),
                SurveyResult.create(application, "질문2", "질문2", 1),
                SurveyResult.create(application, "질문3", "질문3", 2)
        );
    }

}
