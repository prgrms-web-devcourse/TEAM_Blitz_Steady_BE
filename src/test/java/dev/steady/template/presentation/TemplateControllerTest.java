package dev.steady.template.presentation;

import com.epages.restdocs.apispec.Schema;
import dev.steady.auth.domain.Authentication;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.template.fixture.TemplateFixture.createDetailTemplateResponse;
import static dev.steady.template.fixture.TemplateFixture.createTemplateRequest;
import static dev.steady.template.fixture.TemplateFixture.createTemplateResponse;
import static dev.steady.template.fixture.TemplateFixture.createUpdateTemplateRequest;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TemplateControllerTest extends ControllerTestConfig {

    @DisplayName("인증된 사용자의 템플릿 생성요청을 받아 204 상태코드를 반환한다.")
    @Test
    void createTemplateTest() throws Exception {
        Long userId = 1L;
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);

        var request = createTemplateRequest();

        mockMvc.perform(post("/api/v1/template")
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("template-create",
                                resourceDetails().tag("템플릿").description("템플릿 생성 요청")
                                        .requestSchema(Schema.schema("CreateTemplateRequest")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("제목"),
                                        fieldWithPath("questions").type(ARRAY).description("템플릿에 포함될 질문들의 리스트")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @DisplayName("인증된 사용자의 템플릿 목록 요청을 받아 200 상태코드를 반환한다.")
    @Test
    void getTemplatesTest() throws Exception {
        Long userId = 1L;
        var userInfo = createUserInfo(userId);
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);
        var response = createTemplateResponse();
        given(templateService.getTemplates(userInfo)).willReturn(response);

        mockMvc.perform(get("/api/v1/template")
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("template-summary",
                                resourceDetails().tag("템플릿").description("템플릿 생성 요청")
                                        .responseSchema(Schema.schema("TemplateResponses")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("templates[]").type(ARRAY).description("템플릿 목록"),
                                        fieldWithPath("templates[].id").type(NUMBER).description("템플릿 식별자"),
                                        fieldWithPath("templates[].title").type(STRING).description("템플릿 제목"),
                                        fieldWithPath("templates[].createdAt").type(STRING).description("템플릿 생성시간")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));

    }

    @DisplayName("인증된 사용자의 템플릿 상세조회 요청을 받아 200 상태코드를 반환한다.")
    @Test
    void getDetailTemplateTest() throws Exception {
        Long userId = 1L;
        var userInfo = createUserInfo(userId);
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);
        var response = createDetailTemplateResponse();
        given(templateService.getDetailTemplate(userInfo, 1L)).willReturn(response);

        mockMvc.perform(get("/api/v1/template/{templateId}", 1L)
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("template-detail",
                                resourceDetails().tag("템플릿").description("템플릿 상세 조회")
                                        .responseSchema(Schema.schema("TemplateDetailResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                pathParameters(
                                        parameterWithName("templateId").description("템플릿 식별자")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("템플릿 식별자"),
                                        fieldWithPath("title").type(STRING).description("템플릿 제목"),
                                        fieldWithPath("questions[]").type(ARRAY).description("질문 목록"),
                                        fieldWithPath("createdAt").type(STRING).description("템플릿 생성시간"),
                                        fieldWithPath("updatedAt").type(STRING).description("템플릿 수정시간")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("인증된 사용자의 템플릿 삭제요청을 받아 204 상태코드를 반환한다.")
    @Test
    void deleteTemplateTest() throws Exception {
        Long userId = 1L;
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);

        mockMvc.perform(delete("/api/v1/template/{templateId}", 1L)
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("template-delete",
                                resourceDetails().tag("템플릿").description("템플릿 삭제"),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                pathParameters(
                                        parameterWithName("templateId").description("템플릿 식별자")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @DisplayName("인증된 사용자의 템플릿 수정요청을 받아 204 상태코드를 반환한다.")
    @Test
    void updateTemplateTest() throws Exception {
        Long userId = 1L;
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);

        var request = createUpdateTemplateRequest();

        mockMvc.perform(patch("/api/v1/template/{templateId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("template-update",
                                resourceDetails().tag("템플릿").description("템플릿 수정 요청")
                                        .requestSchema(Schema.schema("UpdateTemplateRequest")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                pathParameters(
                                        parameterWithName("templateId").description("템플릿 식별자")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("변경할 제목"),
                                        fieldWithPath("questions").type(ARRAY).description("템플릿에 변경될 질문 리스트")
                                )
                        )
                ).andExpect(status().isOk());
    }

}
