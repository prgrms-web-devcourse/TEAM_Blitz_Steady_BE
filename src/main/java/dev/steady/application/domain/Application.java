package dev.steady.application.domain;

import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static dev.steady.application.domain.ApplicationStatus.WAITING;

@Entity
@Getter
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady_id")
    private Steady steady;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<SurveyResult> surveyResults = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application(User user, Steady steady) {
        this.user = user;
        this.steady = steady;
        this.status = WAITING;
    }

    public void addSurveyResult(SurveyResult surveyResult) {
        surveyResult.setApplication(this);
        surveyResults.add(surveyResult);
    }

}
