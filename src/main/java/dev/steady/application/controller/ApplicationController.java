package dev.steady.application.controller;

import dev.steady.application.dto.request.ApplicationPageRequest;
import dev.steady.application.dto.request.ApplicationStatusUpdateRequest;
import dev.steady.application.dto.request.ApplicationUpdateAnswerRequest;
import dev.steady.application.dto.request.SurveyResultRequest;
import dev.steady.application.dto.response.ApplicationDetailResponse;
import dev.steady.application.dto.response.ApplicationSummaryResponse;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.application.dto.response.MyApplicationSummaryResponse;
import dev.steady.application.dto.response.SliceResponse;
import dev.steady.application.service.ApplicationService;
import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/steadies/{steadyId}/applications")
    public ResponseEntity<CreateApplicationResponse> createApplication(@PathVariable Long steadyId,
                                                                       @RequestBody List<SurveyResultRequest> request,
                                                                       @Auth UserInfo userInfo) {
        CreateApplicationResponse response = applicationService.createApplication(steadyId, request, userInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/steadies/{steadyId}/applications")
    public ResponseEntity<SliceResponse<ApplicationSummaryResponse>> getApplications(@PathVariable Long steadyId,
                                                                                     @Auth UserInfo userInfo,
                                                                                     ApplicationPageRequest request) {
        Pageable pageable = request.toPageable();
        SliceResponse<ApplicationSummaryResponse> response = applicationService.getApplications(steadyId, userInfo, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications/my-list")
    public ResponseEntity<SliceResponse<MyApplicationSummaryResponse>> getMyApplications(@Auth UserInfo userInfo,
                                                                                         ApplicationPageRequest request) {
        Pageable pageable = request.toPageable();
        SliceResponse<MyApplicationSummaryResponse> response = applicationService.getMyApplications(userInfo, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<ApplicationDetailResponse> getApplicationDetail(@PathVariable Long applicationId,
                                                                          @Auth UserInfo userInfo) {
        ApplicationDetailResponse applicationDetail = applicationService.getApplicationDetail(applicationId, userInfo);
        return ResponseEntity.ok(applicationDetail);
    }

    @PatchMapping("/applications/{applicationId}")
    public ResponseEntity<Void> updateApplicationAnswers(@PathVariable Long applicationId,
                                                         @RequestBody ApplicationUpdateAnswerRequest request,
                                                         @Auth UserInfo userInfo) {
        applicationService.updateApplicationAnswer(applicationId, request, userInfo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<Void> updateApplicationStatus(@PathVariable Long applicationId,
                                                        @RequestBody ApplicationStatusUpdateRequest request,
                                                        @Auth UserInfo userInfo) {
        applicationService.updateStatusOfApplication(applicationId, request, userInfo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId,
                                                  @Auth UserInfo userInfo) {
        applicationService.deleteApplication(applicationId, userInfo);
        return ResponseEntity.noContent().build();
    }

}
