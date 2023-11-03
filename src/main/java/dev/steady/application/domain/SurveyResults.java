package dev.steady.application.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyResults {

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResult> values = new ArrayList<>();

    public void addSurveyResult(SurveyResult surveyResult, Application application) {
        surveyResult.setApplication(application);
        values.add(surveyResult);
    }

}
