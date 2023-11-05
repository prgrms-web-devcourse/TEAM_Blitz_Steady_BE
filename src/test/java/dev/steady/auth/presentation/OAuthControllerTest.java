package dev.steady.auth.presentation;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.dto.response.LogInResponse;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.auth.fixture.OAuthFixture.createAuthCode;
import static dev.steady.auth.fixture.OAuthFixture.createLogInResponseForUserNotExist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OAuthControllerTest extends ControllerTestConfig {
    @ParameterizedTest
    @EnumSource(Platform.class)
    @DisplayName("각 플랫폼별 인가코드 요청 URL을 반환할 수 있다.")
    void getAuthorizationCodeRequestUrl(Platform platform) throws Exception {
        // given
        String platformString = platform.name().toLowerCase();
        URI authCodeRequestUrl = new URI("https://example.com/oauth");

        // when
        when(oAuthService.getAuthCodeRequestUrlProvider(platform)).thenReturn(authCodeRequestUrl);

        // then
        mockMvc.perform(get("/api/v1/auth/{platform}", platformString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("auth-v1-getAuthCodeRequestUrl", resourceDetails().tag("인증")
                                .description("플랫폼별 인가코드 요청 URL 불러오기"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("platform").description("소셜로그인 플랫폼")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("인가 코드 요청 URL")
                        )))
                .andExpect(header().string("Location", authCodeRequestUrl.toString()));

    }

    @Test
    @DisplayName("카카오 로그인을 하여 계정 ID와 최초 로그인 여부를 반환할 수 있다.")
    void kakaoLogInforUserNotExist() throws Exception {
        // given
        String platform = Platform.KAKAO.name().toLowerCase();
        String authCode = createAuthCode();
        LogInResponse logInResponse = createLogInResponseForUserNotExist();
        given(oAuthService.logIn(any(), any())).willReturn(logInResponse);

        // when, then
        mockMvc.perform(get("/api/v1/auth/{platform}/callback", platform)
                        .queryParam("code", authCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-v1-login-new", resourceDetails().tag("인증")
                                .description("카카오 로그인-최초"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("platform").description("소셜로그인 플랫폼")
                        ),
                        queryParameters(
                                parameterWithName("code").description("카카오 인가 코드")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("계정 id"),
                                fieldWithPath("isNew").type(BOOLEAN).description("최초 로그인 여부"),
                                fieldWithPath("token").type(NULL).description("서비스 이용 토큰")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(logInResponse.id()))
                .andExpect(jsonPath("isNew").isBoolean())
                .andExpect(jsonPath("token").isEmpty());
    }

}
