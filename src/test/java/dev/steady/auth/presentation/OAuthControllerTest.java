package dev.steady.auth.presentation;

import com.epages.restdocs.apispec.Schema;
import dev.steady.auth.domain.Platform;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.auth.fixture.OAuthFixture.createAuthCode;
import static dev.steady.auth.fixture.OAuthFixture.createAuthCodeRequestUrl;
import static dev.steady.auth.fixture.OAuthFixture.createLogInResponseForUserExist;
import static dev.steady.auth.fixture.OAuthFixture.createLogInResponseForUserNotExist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OAuthControllerTest extends ControllerTestConfig {
    @ParameterizedTest
    @EnumSource(Platform.class)
    @DisplayName("각 플랫폼별 인가코드 요청 URL을 반환할 수 있다.")
    void getAuthorizationCodeRequestUrl(Platform platform) throws Exception {
        // given
        var platformString = platform.name().toLowerCase();
        var authCodeRequestUrl = createAuthCodeRequestUrl();

        // when
        when(oAuthService.getAuthCodeRequestUrlProvider(platform)).thenReturn(authCodeRequestUrl);

        // then
        mockMvc.perform(get("/api/v1/auth/{platform}", platformString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("auth-v1-getAuthCodeRequestUrl",
                                resourceDetails().tag("인증").description("플랫폼별 인가코드 요청 URL 불러오기")
                                        .responseSchema(Schema.schema("OAuthCodeRequestUrlResponse")),
                                pathParameters(
                                        parameterWithName("platform").description("소셜로그인 플랫폼")
                                ),
                                responseHeaders(
                                        headerWithName("Location").description("인가 코드 요청 URL")
                                )
                        )
                )
                .andExpect(header().string("Location", authCodeRequestUrl.toString()));

    }

    @Test
    @DisplayName("카카오 로그인을 하여 계정 ID와 최초 로그인 여부를 반환할 수 있다.")
    void kakaoLogInforUserNotExist() throws Exception {
        // given
        var platform = Platform.KAKAO.name().toLowerCase();
        var authCode = createAuthCode();
        var response = createLogInResponseForUserNotExist();
        given(oAuthService.logIn(any(), any())).willReturn(response);

        // when, then
        mockMvc.perform(get("/api/v1/auth/{platform}/callback", platform)
                        .queryParam("code", authCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-v1-login-new", resourceDetails().tag("인증")
                                        .description("카카오 로그인-최초")
                                        .responseSchema(Schema.schema("LogInResponse")),
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
                                )
                        )
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));

    }

    @Test
    @DisplayName("카카오 로그인을 하여 액세스 토큰을 반환할 수 있다.")
    void kakaoLogInforUserExist() throws Exception {
        // given
        var platform = Platform.KAKAO.name().toLowerCase();
        var authCode = createAuthCode();
        var response = createLogInResponseForUserExist();
        given(oAuthService.logIn(any(), any())).willReturn(response);

        // when, then
        mockMvc.perform(get("/api/v1/auth/{platform}/callback", platform)
                        .queryParam("code", authCode))
                .andDo(print())
                .andDo(document("auth-v1-login-kakao", resourceDetails().tag("인증")
                                        .description("카카오 로그인"),
                                pathParameters(
                                        parameterWithName("platform").description("소셜로그인 플랫폼")
                                ),
                                queryParameters(
                                        parameterWithName("code").description("카카오 인가 코드")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(NUMBER).description("계정 id"),
                                        fieldWithPath("isNew").type(BOOLEAN).description("최초 로그인 여부"),
                                        fieldWithPath("token.accessToken").type(STRING).description("서비스 이용 액세스 토큰"),
                                        fieldWithPath("token.refreshToken").type(STRING).description("서비스 이용 리프레시 토큰")
                                )
                        )
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
