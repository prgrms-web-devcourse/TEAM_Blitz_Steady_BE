package dev.steady.review.service;

import dev.steady.global.auth.UserInfo;
import dev.steady.global.exception.ForbiddenException;
import dev.steady.global.exception.InvalidStateException;
import dev.steady.review.domain.Card;
import dev.steady.review.domain.Review;
import dev.steady.review.domain.UserCard;
import dev.steady.review.domain.repository.CardRepository;
import dev.steady.review.domain.repository.ReviewRepository;
import dev.steady.review.domain.repository.UserCardRepository;
import dev.steady.review.dto.request.ReviewCreateRequest;
import dev.steady.review.dto.response.CardsResponse;
import dev.steady.review.dto.response.ReviewInfoResponse;
import dev.steady.review.dto.response.ReviewMyResponse;
import dev.steady.review.dto.response.ReviewSteadyResponse;
import dev.steady.review.dto.response.ReviewSwitchResponse;
import dev.steady.review.dto.response.RevieweeResponse;
import dev.steady.review.dto.response.ReviewsBySteadyResponse;
import dev.steady.review.dto.response.UserCardResponse;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Participants;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static dev.steady.review.exception.ReviewErrorCode.REVIEWEE_EQUALS_REVIEWER;
import static dev.steady.review.exception.ReviewErrorCode.REVIEW_AUTH_FAILURE;
import static dev.steady.review.exception.ReviewErrorCode.REVIEW_DUPLICATE;
import static dev.steady.review.exception.ReviewErrorCode.REVIEW_NOT_ENABLED;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SteadyRepository steadyRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;

    @Transactional
    public Long createReview(Long steadyId, ReviewCreateRequest request, UserInfo userInfo) {
        Long reviewerId = userInfo.userId();
        Long revieweeId = request.revieweeId();
        if (Objects.equals(revieweeId, reviewerId)) {
            throw new InvalidStateException(REVIEWEE_EQUALS_REVIEWER);
        }

        Steady steady = getSteady(steadyId);
        Participants participants = steady.getParticipants();

        if (!steady.isReviewEnabled()) {
            throw new InvalidStateException(REVIEW_NOT_ENABLED);
        }

        Participant reviewer = participants.getParticipantByUserId(reviewerId);
        Participant reviewee = participants.getParticipantByUserId(revieweeId);

        if (isAlreadyReviewed(reviewer, reviewee, steady)) {
            throw new InvalidStateException(REVIEW_DUPLICATE);
        }

        Review review = request.toEntity(reviewer, reviewee, steady);
        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }

    @Transactional
    public void createUserCards(ReviewCreateRequest request) {
        if (request.cardsId().isEmpty()) {
            return;
        }
        
        User reviewee = userRepository.getUserBy(request.revieweeId());
        List<Card> cards = getCards(request.cardsId());
        List<UserCard> userCards = cards.stream()
                .map(card -> new UserCard(reviewee, card))
                .toList();

        userCardRepository.saveAll(userCards);
    }

    /**
     * 리뷰의 공개 여부(isPublic)을 변경하고 그 결과를 반환한다.
     *
     * @param reviewId 리뷰 식별자
     * @param userInfo 사용자 정보
     * @return 리뷰 공개 여부 변경된 결과
     */
    @Transactional
    public ReviewSwitchResponse switchReviewIsPublic(Long reviewId, UserInfo userInfo) {
        Review review = reviewRepository.getById(reviewId);
        Participant reviewee = review.getReviewee();
        if (!Objects.equals(reviewee.getUserId(), userInfo.userId())) {
            throw new ForbiddenException(REVIEW_AUTH_FAILURE);
        }
        review.switchIsPublic();
        return ReviewSwitchResponse.from(review);
    }

    @Transactional(readOnly = true)
    public ReviewMyResponse getMyCardsAndReviews(UserInfo userInfo) {
        User user = userRepository.getUserBy(userInfo.userId());
        List<UserCardResponse> userCards = userCardRepository.getCardCountByUser(user);
        List<ReviewsBySteadyResponse> reviews = reviewRepository.getAllReviewsByRevieweeUser(user);
        return ReviewMyResponse.of(userCards, reviews);
    }

    @Transactional(readOnly = true)
    public ReviewInfoResponse getReviewInfo(Long steadyId, UserInfo userInfo) {
        Steady steady = steadyRepository.getSteady(steadyId);

        Participants steadyParticipants = steady.getParticipants();
        Participant reviewer = steadyParticipants.getParticipantByUserId(userInfo.userId());

        ReviewSteadyResponse steadyResponse = ReviewSteadyResponse.from(steady, reviewer);

        List<Participant> participants = steadyParticipants.getAllParticipants();
        List<RevieweeResponse> revieweeResponses = participants.stream()
                .filter(participant -> !Objects.equals(reviewer, participant)
                                       && !isAlreadyReviewed(reviewer, participant, steady))
                .map(RevieweeResponse::from)
                .toList();

        return ReviewInfoResponse.of(steadyResponse, revieweeResponses);
    }

    @Transactional(readOnly = true)
    public CardsResponse getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return CardsResponse.from(cards);
    }

    private boolean isAlreadyReviewed(Participant reviewer, Participant reviewee, Steady steady) {
        return reviewRepository.existsByReviewerAndRevieweeAndSteady(
                reviewer,
                reviewee,
                steady
        );
    }

    private List<Card> getCards(List<Long> cardsId) {
        return cardsId.stream()
                .map(this::getCard)
                .toList();
    }

    private Card getCard(Long cardId) {
        return cardRepository.getById(cardId);
    }

    private Steady getSteady(Long steadyId) {
        return steadyRepository.getSteady(steadyId);
    }

}
