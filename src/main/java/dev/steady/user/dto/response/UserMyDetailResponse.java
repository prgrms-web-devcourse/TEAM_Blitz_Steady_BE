package dev.steady.user.dto.response;

import dev.steady.auth.domain.Platform;
import dev.steady.user.domain.User;
import dev.steady.user.domain.UserStack;
import lombok.Builder;

import java.util.List;

@Builder
public record UserMyDetailResponse(
        Platform platform,
        Long userId,
        String profileImage,
        String nickname,
        String bio,
        PositionResponse position,
        List<StackResponse> stacks
) {

    public static UserMyDetailResponse of(Platform platform, User user, List<UserStack> userStacks) {
        PositionResponse position = PositionResponse.from(user.getPosition());
        List<StackResponse> stacks = userStacks.stream()
                .map(userStack -> StackResponse.from(userStack.getStack()))
                .toList();

        return UserMyDetailResponse.builder()
                .platform(platform)
                .userId(user.getId())
                .profileImage(user.getProfileImage())
                .bio(user.getBio())
                .nickname(user.getNickname())
                .position(position)
                .stacks(stacks)
                .build();
    }

}
