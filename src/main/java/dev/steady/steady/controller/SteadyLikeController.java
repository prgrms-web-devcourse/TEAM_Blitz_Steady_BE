package dev.steady.steady.controller;

import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import dev.steady.steady.dto.response.SteadyLikeResponse;
import dev.steady.steady.service.SteadyLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/steadies")
public class SteadyLikeController {

    private final SteadyLikeService steadyLikeService;

    @PatchMapping("/{steadyId}/like")
    public ResponseEntity<SteadyLikeResponse> updateLikeStatus(@PathVariable Long steadyId,
                                                               @Auth UserInfo userInfo) {
        SteadyLikeResponse response = steadyLikeService.updateSteadyLike(steadyId, userInfo);
        return ResponseEntity.ok(response);
    }

}
