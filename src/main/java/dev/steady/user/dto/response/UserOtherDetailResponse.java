package dev.steady.user.dto.response;

import dev.steady.review.dto.response.UserCardResponse;

import java.util.List;

public record UserOtherDetailResponse(
        UserDetailResponse user,
        List<UserCardResponse> userCards,
        List<String> reviews,
        boolean isDeleted
) {

    public static UserOtherDetailResponse of(UserDetailResponse user,
                                             List<UserCardResponse> userCards,
                                             List<String> reviews) {
        return new UserOtherDetailResponse(
                user,
                userCards,
                reviews,
                false
        );
    }

    public static UserOtherDetailResponse deletedUser() {
        return new UserOtherDetailResponse(
                null,
                null,
                null,
                true
        );
    }

}
