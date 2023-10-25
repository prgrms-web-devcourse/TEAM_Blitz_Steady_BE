package dev.steady.steady.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SteadySearchResponse(
        Long id,
        String writer,
        String profileImage,
        String title, // 모집글 제목
        String type,
        LocalDate deadline,
        LocalDateTime createdAt,
        int recruitCount,
        int numberOfParticipants
        // TODO: 2023-10-25  해쉬태그, 조회수, 댓글 수, 기술 스택
){

}
