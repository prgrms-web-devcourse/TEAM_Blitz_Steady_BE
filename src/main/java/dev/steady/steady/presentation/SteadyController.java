package dev.steady.steady.presentation;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.dto.response.PageResponse;
import dev.steady.steady.dto.response.SteadyDetailResponse;
import dev.steady.steady.dto.response.SteadySearchResponse;
import dev.steady.steady.service.SteadyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/steadies")
public class SteadyController {

    private final SteadyService steadyService;

    @PostMapping
    public ResponseEntity<Void> createSteady(@RequestBody SteadyCreateRequest request, AuthContext authContext) {
        Long steadyId = steadyService.create(request, authContext);
        return ResponseEntity.created(URI.create(String.format("/api/v1/steadies/%d/detail", steadyId))).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<SteadySearchResponse>> getSteadiesPage(@RequestBody SteadyPageRequest request) {
        PageResponse<SteadySearchResponse> response = steadyService.getSteadies(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{steadyId}")
    public ResponseEntity<SteadyDetailResponse> getDetailSteady(@PathVariable Long steadyId,
                                                                @RequestBody SteadyPageRequest request,
                                                                AuthContext authContext) {
        SteadyDetailResponse response = steadyService.getDetailSteady(steadyId, authContext);
        return ResponseEntity.ok(response);
    }

}
