package dev.steady.application.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static dev.steady.application.domain.ApplicationStatus.WAITING;

@Entity
@Getter
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady_id")
    private Steady steady;

    @Embedded
    private SurveyResults surveyResults = new SurveyResults();

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application(User user, Steady steady) {
        this.user = user;
        this.steady = steady;
        this.status = WAITING;
    }

    public void checkAccessOrThrow(User user) {
        if (!hasAccess(user)) {
            throw new IllegalArgumentException();
        }
    }

    public void addSurveyResult(SurveyResult surveyResult) {
        this.surveyResults.addSurveyResult(surveyResult, this);
    }

    public void updateStatus(ApplicationStatus status, User user) {
        if (steady.isLeader(user) && this.status == WAITING) {
            this.status = status;
            return;
        }
        throw new IllegalArgumentException();
    }

    private boolean hasAccess(User user) {
        return isCurrentUser(user) || isLeader(user);
    }

    private boolean isCurrentUser(User user) {
        return this.user.equals(user);
    }

    private boolean isLeader(User user) {
        return steady.isLeader(user);
    }

}
