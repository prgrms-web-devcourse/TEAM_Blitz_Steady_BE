package dev.steady.user.controller;

import dev.steady.user.dto.response.PositionResponses;
import dev.steady.user.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/positions")
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<PositionResponses> getPositions() {
        PositionResponses response = positionService.getPositions();
        return ResponseEntity.ok(response);
    }

}
