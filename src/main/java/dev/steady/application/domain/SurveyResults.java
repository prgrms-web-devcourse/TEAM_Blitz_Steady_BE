package dev.steady.application.domain;

import dev.steady.global.exception.InvalidValueException;
import lombok.Getter;

import java.util.List;

import static dev.steady.application.exception.SurveyResultErrorCode.INVALID_SURVEY_SIZE;

@Getter
public class SurveyResults {

    private final List<SurveyResult> values;

    public SurveyResults(List<SurveyResult> values) {
        this.values = values;
    }

    public void updateAnswers(List<String> answers) {
        if (values.size() != answers.size()) {
            throw new InvalidValueException(INVALID_SURVEY_SIZE);
        }
        for (int i = 0; i < values.size(); i++) {
            SurveyResult surveyResult = values.get(i);
            String newAnswer = answers.get(i);
            surveyResult.updateAnswer(newAnswer);
        }
    }

}
