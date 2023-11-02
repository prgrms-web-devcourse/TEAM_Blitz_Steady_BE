package dev.steady.application.presentation;

import dev.steady.application.dto.request.SurveyResultRequest;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.application.service.ApplicationService;
import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/steadies/{steadyId}/application")
    public ResponseEntity<CreateApplicationResponse> createApplication(@PathVariable Long steadyId,
                                                                       @RequestBody List<SurveyResultRequest> request,
                                                                       @Auth UserInfo userInfo) {
        CreateApplicationResponse response = applicationService.createApplication(steadyId, request, userInfo);
        return ResponseEntity.ok(response);
    }

}
