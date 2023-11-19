package dev.steady.review.controller;

import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import dev.steady.review.dto.request.ReviewCreateRequest;
import dev.steady.review.dto.request.ReviewUpdateRequest;
import dev.steady.review.dto.response.ReviewMyResponse;
import dev.steady.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("steadies/{steadyId}/review")
    public ResponseEntity<Void> createReview(@PathVariable Long steadyId,
                                             @RequestBody ReviewCreateRequest request,
                                             @Auth UserInfo userInfo) {
        Long reviewId = reviewService.createReview(steadyId, request, userInfo);
        reviewService.createUserCards(request);

        return ResponseEntity.created(
                        URI.create(String.format("/api/v1/reviews/%d", reviewId)))
                .build();
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReviewIsPublic(@PathVariable Long reviewId,
                                                     @RequestBody ReviewUpdateRequest request,
                                                     @Auth UserInfo userInfo) {
        reviewService.updateReviewIsPublic(reviewId, request, userInfo);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews/my")
    public ResponseEntity<ReviewMyResponse> getMyCardsAndReviews(@Auth UserInfo userInfo) {
        ReviewMyResponse response = reviewService.getMyCardsAndReviews(userInfo);
        return ResponseEntity.ok(response);
    }

}
