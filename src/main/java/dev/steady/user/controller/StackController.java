package dev.steady.user.controller;

import dev.steady.user.dto.response.StacksResponse;
import dev.steady.user.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stacks")
public class StackController {

    private final StackService stackService;

    @GetMapping
    public ResponseEntity<StacksResponse> getStacks() {
        StacksResponse response = stackService.getStacks();
        return ResponseEntity.ok(response);
    }

}
