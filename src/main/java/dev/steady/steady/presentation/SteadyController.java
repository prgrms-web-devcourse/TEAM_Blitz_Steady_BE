package dev.steady.steady.presentation;

import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import dev.steady.steady.dto.SearchConditionDto;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.dto.request.SteadySearchRequest;
import dev.steady.steady.dto.response.PageResponse;
import dev.steady.steady.dto.response.SteadyDetailResponse;
import dev.steady.steady.dto.response.SteadySearchResponse;
import dev.steady.steady.service.SteadyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<Void> createSteady(@RequestBody SteadyCreateRequest request,
                                             @Auth UserInfo userInfo) {
        Long steadyId = steadyService.create(request, userInfo);
        return ResponseEntity.created(URI.create(String.format("/api/v1/steadies/%d/detail", steadyId))).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<SteadySearchResponse>> getSteadiesPage(SteadyPageRequest request) {
        Pageable pageable = request.toPageable();
        PageResponse<SteadySearchResponse> response = steadyService.getSteadies(pageable);
        return ResponseEntity.ok(response);
    }

    // TODO: 2023-11-04 아래 메서드의 엔드포인트와 메서드명 고민
    @GetMapping("/search")
    public ResponseEntity<PageResponse<SteadySearchResponse>> getSteadies(SteadySearchRequest request) {
        SearchConditionDto condition = SearchConditionDto.from(request);
        Pageable pageable = request.toPageable();
        PageResponse<SteadySearchResponse> response = steadyService.getSteadies(condition, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{steadyId}")
    public ResponseEntity<SteadyDetailResponse> getDetailSteady(@PathVariable Long steadyId,
                                                                @Auth UserInfo userInfo) {
        SteadyDetailResponse response = steadyService.getDetailSteady(steadyId, userInfo);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{steadyId}/promote")
    public ResponseEntity<Void> promoteSteady(@PathVariable Long steadyId, @Auth UserInfo userInfo) {
        steadyService.promoteSteady(steadyId, userInfo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{steadyId}/finish")
    public ResponseEntity<Void> finishSteady(@PathVariable Long steadyId, @Auth UserInfo userInfo) {
        steadyService.finishSteady(steadyId, userInfo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{steadyId}/delete")
    public ResponseEntity<Void> deleteSteady(@PathVariable Long steadyId, @Auth UserInfo userInfo) {
        steadyService.deleteSteady(steadyId, userInfo);
        return ResponseEntity.noContent().build();
    }

}
