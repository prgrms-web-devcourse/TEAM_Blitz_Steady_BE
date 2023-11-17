package dev.steady.user.dto.response;

import dev.steady.user.domain.User;
import dev.steady.user.domain.UserStack;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDetailResponse(
        Long userId,
        String profileImage,
        String nickname,
        String bio,
        PositionResponse position,
        List<StackResponse> stacks
) {

    public static UserDetailResponse of(User user, List<UserStack> userStacks) {
        PositionResponse position = PositionResponse.from(user.getPosition());
        List<StackResponse> stacks = userStacks.stream()
                .map(userStack -> StackResponse.from(userStack.getStack()))
                .toList();

        return UserDetailResponse.builder()
                .userId(user.getId())
                .profileImage(user.getProfileImage())
                .nickname(user.getNickname())
                .bio(user.getBio())
                .position(position)
                .stacks(stacks)
                .build();
    }

}
