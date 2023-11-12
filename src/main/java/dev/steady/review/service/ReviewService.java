package dev.steady.review.service;

import dev.steady.global.auth.UserInfo;
import dev.steady.global.exception.ForbiddenException;
import dev.steady.review.domain.Review;
import dev.steady.review.domain.repository.CardRepository;
import dev.steady.review.domain.repository.ReviewRepository;
import dev.steady.review.domain.repository.UserCardRepository;
import dev.steady.review.dto.ReviewCreateRequest;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.ParticipantRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.exception.InvalidStateException;
import dev.steady.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static dev.steady.review.exception.ReviewErrorCode.REVIEWEE_EQUALS_REVIEWER;
import static dev.steady.review.exception.ReviewErrorCode.REVIEWER_ID_MISMATCH;
import static dev.steady.review.exception.ReviewErrorCode.STEADY_NOT_FINISHED;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SteadyRepository steadyRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;

    @Transactional
    public Long createReview(Long steadyId, ReviewCreateRequest request, UserInfo userInfo) {
        Long reviewerId = request.reviewerId();
        Long revieweeId = request.revieweeId();
        if (Objects.equals(revieweeId, reviewerId)) {
            throw new InvalidStateException(REVIEWEE_EQUALS_REVIEWER);
        }

        Long reviewerUserId = getParticipant(reviewerId).getUser().getId();
        if (!Objects.equals(reviewerUserId, userInfo.userId())) {
            throw new ForbiddenException(REVIEWER_ID_MISMATCH);
        }

        Steady steady = getSteady(steadyId);
        if (!steady.isFinished()) {
            throw new InvalidStateException(STEADY_NOT_FINISHED);
        }

        Participant reviewer = getParticipant(reviewerId);
        Participant reviewee = getParticipant(revieweeId);

        Review review = request.toEntity(reviewer, reviewee, steady);
        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }

    private Steady getSteady(Long steadyId) {
        return steadyRepository.getSteady(steadyId);
    }

    private Participant getParticipant(Long participantId) {
        return participantRepository.getById(participantId);
    }

}
