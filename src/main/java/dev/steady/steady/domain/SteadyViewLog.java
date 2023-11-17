package dev.steady.steady.domain;

import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "steady_view_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyViewLog extends BaseEntity {

    private static final int REPEAT_VIEW_DELAY_HOURS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long steadyId;

    public SteadyViewLog(Long userId, Long steadyId) {
        this.userId = userId;
        this.steadyId = steadyId;
    }

    public boolean checkThreeHoursPassed() {
        LocalDateTime createdAt = getCreatedAt();
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        return duration.toHours() >= REPEAT_VIEW_DELAY_HOURS;
    }

}
