package dev.steady.application.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "survey_results")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private int sequence;

    private SurveyResult(Application application,
                         String question,
                         String answer,
                         int sequence) {
        this.application = application;
        this.question = question;
        this.answer = answer;
        this.sequence = sequence;
    }

    public static SurveyResult create(Application application, String question, String answer, int sequence) {
        return new SurveyResult(application,
                question,
                answer,
                sequence);
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

}
