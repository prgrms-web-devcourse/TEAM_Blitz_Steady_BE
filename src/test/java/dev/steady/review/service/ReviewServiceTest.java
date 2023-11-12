package dev.steady.review.service;

import dev.steady.review.domain.Card;
import dev.steady.review.domain.Review;
import dev.steady.review.domain.UserCard;
import dev.steady.review.domain.repository.CardRepository;
import dev.steady.review.domain.repository.ReviewRepository;
import dev.steady.review.domain.repository.UserCardRepository;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.ParticipantRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.IntStream;

import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.review.fixture.ReviewFixtures.createCard;
import static dev.steady.review.fixture.ReviewFixtures.createReviewCreateRequest;
import static dev.steady.steady.fixture.SteadyFixtures.createFinishedSteady;
import static dev.steady.steady.fixture.SteadyFixtures.createParticipant;
import static dev.steady.user.fixture.UserFixtures.createPosition;
import static dev.steady.user.fixture.UserFixtures.createStack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SteadyRepository steadyRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserCardRepository userCardRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private StackRepository stackRepository;

    private Steady steady;
    private Participant reviewer;
    private Participant reviewee;

    @BeforeEach
    void setUp() {
        var position = positionRepository.save(createPosition());
        var stack = stackRepository.save(createStack());

        var firstUser = userRepository.save(UserFixtures.createFirstUser(position));
        var secoundUser = userRepository.save(UserFixtures.createSecondUser(position));

        this.steady = steadyRepository.save(createFinishedSteady(firstUser, stack));

        this.reviewer = participantRepository.save(createParticipant(firstUser, steady));
        this.reviewee = participantRepository.save(createParticipant(secoundUser, steady));
    }

    @AfterEach
    void tearDown() {
        userCardRepository.deleteAll();
        reviewRepository.deleteAll();
        participantRepository.deleteAll();
        steadyRepository.deleteAll();
        userRepository.deleteAll();
        cardRepository.deleteAll();
        stackRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @DisplayName("종료된 스테디에 대하여 리뷰를 생성한다.")
    @Test
    void createReview() {
        // given
        var userInfo = createUserInfo(reviewer.getUser().getId());

        // when
        var request = createReviewCreateRequest(
                reviewer.getId(),
                reviewee.getId(),
                List.of(1L, 2L)
        );

        var reviewId = reviewService.createReview(1L, request, userInfo);

        Review review = transactionTemplate.execute(status -> {
            var foundReview = reviewRepository.findById(reviewId).get();
            foundReview.getId();
            foundReview.getReviewer();
            foundReview.getReviewee();
            return foundReview;
        });

        // then
        assertAll(
                () -> assertThat(review.getId()).isEqualTo(reviewId),
                () -> assertThat(review.getReviewer().getId()).isEqualTo(reviewer.getId()),
                () -> assertThat(review.getReviewee().getId()).isEqualTo(reviewee.getId())
        );
    }

    @DisplayName("사용자의 리뷰 카드를 생성할 수 있다.")
    @Test
    void createUserCards() {
        // given
        List<Card> cards = IntStream.range(0, 3)
                .mapToObj(i -> createCard())
                .toList();
        cardRepository.saveAll(cards);

        // when
        List<Long> cardIds = List.of(1L, 2L);
        var request = createReviewCreateRequest(
                reviewer.getId(),
                reviewee.getId(),
                cardIds
        );

        List<UserCard> userCards = reviewService.createUserCards(request);

        // then
        assertAll(
                () -> assertThat(userCards).hasSameSizeAs(cardIds)
        );
    }

}
