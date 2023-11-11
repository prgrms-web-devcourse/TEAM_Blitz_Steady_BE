package dev.steady.steady.dto.response;

import dev.steady.steady.domain.Participant;

import java.util.List;

public record ParticipantsResponse(
        List<ParticipantResponse> participants
) {

    public static ParticipantsResponse from(List<Participant> participants) {
        List<ParticipantResponse> responses = participants.stream()
                .map(ParticipantResponse::from)
                .toList();
        return new ParticipantsResponse(responses);
    }

}
