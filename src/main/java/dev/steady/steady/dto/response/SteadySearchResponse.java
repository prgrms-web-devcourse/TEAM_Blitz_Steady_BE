package dev.steady.steady.dto.response;

import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyType;
import dev.steady.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SteadySearchResponse(
        Long id,
        String leaderNickname,
        String profileImage,
        String title, // 모집글 제목
        SteadyType type,
        LocalDate deadline,
        LocalDateTime createdAt,
        int recruitCount,
        int numberOfParticipants
        // TODO: 2023-10-25  해쉬태그, 조회수, 댓글 수, 기술 스택
){

    public static SteadySearchResponse from(Steady steady) {
        User leader = steady.getParticipants().getLeader();
        return new SteadySearchResponse(steady.getId(),
                leader.getNickname(),
                leader.getProfileImage(),
                steady.getTitle(),
                steady.getType(),
                steady.getDeadline(),
                steady.getCreatedAt(),
                steady.getRecruitCount(),
                steady.getNumberOfParticipants());
    }

}
