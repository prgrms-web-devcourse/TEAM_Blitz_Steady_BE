package dev.steady.auth.presentation;

import dev.steady.auth.domain.Platform;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

}
