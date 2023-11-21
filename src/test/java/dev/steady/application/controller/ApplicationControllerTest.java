package dev.steady.application.controller;

import com.epages.restdocs.apispec.Schema;
import dev.steady.application.dto.request.ApplicationPageRequest;
import dev.steady.application.dto.request.ApplicationStatusUpdateRequest;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.global.auth.Authentication;
import dev.steady.global.auth.UserInfo;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.application.domain.ApplicationStatus.ACCEPTED;
import static dev.steady.application.fixture.ApplicationFixture.createApplicationDetailResponse;
import static dev.steady.application.fixture.ApplicationFixture.createApplicationSummaryResponse;
import static dev.steady.application.fixture.ApplicationFixture.createMyApplicationSummaryResponse;
import static dev.steady.application.fixture.SurveyResultFixture.createAnswers;
import static dev.steady.application.fixture.SurveyResultFixture.createSurveyResultRequests;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationControllerTest extends ControllerTestConfig {

    @DisplayName("인증된 사용자는 스테디에 신청서를 제출할 수 있다.")
    @Test
    void createApplicationTest() throws Exception {
        long userId = 1L;
        long steadyId = 1L;
        var request = createSurveyResultRequests();
        var auth = new Authentication(userId);
        var userInfo = new UserInfo(userId);
        var response = new CreateApplicationResponse(steadyId);

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);
        when(applicationService.createApplication(steadyId, request, userInfo))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/steadies/{steadyId}/applications", steadyId)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("application-create",
                                resourceDetails().tag("신청서").description("신청서 제출")
                                        .requestSchema(Schema.schema("SurveyResultRequest"))
                                        .responseSchema(Schema.schema("CreateApplicationResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("[]").type(ARRAY).description("템플릿에 포함될 질문들의 리스트"),
                                        fieldWithPath("[].question").type(STRING).description("질문"),
                                        fieldWithPath("[].answer").type(STRING).description("답변")
                                ),
                                responseFields(
                                        fieldWithPath("applicationId").type(NUMBER).description("신청서 식별자")
                                )
                        )
                );
    }

    @DisplayName("인증된 사용자는 스테디에 제출된 신청서를 조회할 수 있다.")
    @Test
    void getApplicationsTest() throws Exception {
        long userId = 1L;
        long steadyId = 1L;
        var auth = new Authentication(userId);
        var userInfo = new UserInfo(userId);
        var response = createApplicationSummaryResponse();
        var page = new ApplicationPageRequest(0, "desc");
        var pageable = page.toPageable();

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);
        when(applicationService.getApplications(steadyId, userInfo, pageable))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/steadies/{steadyId}/applications", steadyId)
                        .header(AUTHORIZATION, TOKEN)
                        .param("page", "0")
                        .param("direction", "desc")
                )
                .andDo(document("application-summary",
                                resourceDetails().tag("신청서").description("신청서 목록 조회")
                                        .responseSchema(Schema.schema("SliceResponse"))
                                        .responseSchema(Schema.schema("ApplicationSummaryResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("content").description("신청서 목록"),
                                        fieldWithPath("content[].applicationId").description("신청서 식별자"),
                                        fieldWithPath("content[].userId").description("유저 식별자"),
                                        fieldWithPath("content[].nickname").description("신청자 닉네임"),
                                        fieldWithPath("content[].profileImage").description("신청자 프로필 사진"),
                                        fieldWithPath("numberOfElements").description("목록의 갯수"),
                                        fieldWithPath("hasNext").description("다음 페이지의 유무")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("사용자의 신청서 리스트를 반환한다.")
    @Test
    void getMyApplicationsTest() throws Exception {
        long userId = 1L;
        var auth = new Authentication(userId);
        var userInfo = new UserInfo(userId);
        var response = createMyApplicationSummaryResponse();
        var page = new ApplicationPageRequest(0, "desc");
        var pageable = page.toPageable();

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);
        when(applicationService.getMyApplications(userInfo, pageable))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/applications/my-list")
                        .header(AUTHORIZATION, TOKEN))
                .andDo(document("MyApplication-Summary",
                                resourceDetails().tag("신청서").description("내 신청서 목록 조회")
                                        .responseSchema(Schema.schema("MyApplicationSummaryResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("content").type(ARRAY).description("신청서 목록"),
                                        fieldWithPath("content[].applicationId").type(NUMBER).description("신청서 식별자"),
                                        fieldWithPath("content[].steadyName").type(STRING).description("스테디 이름"),
                                        fieldWithPath("content[].createdAt").type(STRING).description("신청서 제출일"),
                                        fieldWithPath("content[].status").type(STRING).description("신청서 상태"),
                                        fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지 갯수"),
                                        fieldWithPath("hasNext").type(BOOLEAN).description("다음 페이지 유무")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("인증된 사용자는 스테디에 제출된 신청서를 상세 조회할 수 있다.")
    @Test
    void getApplicationDetailTest() throws Exception {
        long userId = 1L;
        long applicationId = 1L;
        var auth = new Authentication(userId);
        var userInfo = new UserInfo(userId);
        var response = createApplicationDetailResponse();

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);
        when(applicationService.getApplicationDetail(applicationId, userInfo))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/applications/{applicationId}", applicationId)
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("application-detail",
                                resourceDetails().tag("신청서").description("신청서 상세 조회")
                                        .responseSchema(Schema.schema("ApplicationDetailResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("surveys").description("질문과 답변 리스트"),
                                        fieldWithPath("surveys[].question").description("신청서 질문"),
                                        fieldWithPath("surveys[].answer").description("신청서 답변")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("스테디 리더는 제출된 신청서의 상태를 변경할 수 있다.")
    @Test
    void updateApplicationStatusTest() throws Exception {
        long userId = 1L;
        long applicationId = 1L;
        var request = new ApplicationStatusUpdateRequest(ACCEPTED);
        var auth = new Authentication(userId);

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);

        mockMvc.perform(patch("/api/v1/applications/{applicationId}/status", applicationId)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("application-status-change",
                                resourceDetails().tag("신청서").description("신청서 상태 변경")
                                        .requestSchema(Schema.schema("ApplicationStatusUpdateRequest")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("status").type(STRING).description("신청서의 상태")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @DisplayName("신청자는 본인이 제출한 신청서의 답변을 수정할 수 있다.")
    @Test
    void updateApplicationAnswersTest() throws Exception {
        long userId = 1L;
        long applicationId = 1L;
        var request = createAnswers();
        var auth = new Authentication(userId);

        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(auth);

        mockMvc.perform(patch("/api/v1/applications/{applicationId}", applicationId)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(document("application-answer-update",
                resourceDetails().tag("신청서").description("신청서 답변 수정")
                        .requestSchema(Schema.schema("ApplicationUpdateAnswerRequest")),
                requestHeaders(
                        headerWithName(AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("answers").type(ARRAY).description("답변 목록")
                )
        )).andExpect(status().isNoContent());
    }

}
