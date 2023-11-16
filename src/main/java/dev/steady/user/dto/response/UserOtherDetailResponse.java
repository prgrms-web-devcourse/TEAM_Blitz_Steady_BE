package dev.steady.user.dto.response;

import dev.steady.review.dto.response.UserCardResponse;

import java.util.List;

public record UserOtherDetailResponse(
        UserDetailResponse user,
        List<UserCardResponse> userCards,
        List<String> reviews
) {

    public static UserOtherDetailResponse of(UserDetailResponse user,
                                             List<UserCardResponse> userCards,
                                             List<String> reviews) {
        return new UserOtherDetailResponse(
                user,
                userCards,
                reviews
        );
    }

}
