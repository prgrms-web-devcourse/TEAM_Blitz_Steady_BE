package dev.steady.user.presentation;

import com.epages.restdocs.apispec.Schema;
import dev.steady.global.config.ControllerTestConfig;
import dev.steady.user.dto.request.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.auth.domain.Platform.KAKAO;
import static dev.steady.auth.fixture.OAuthFixture.createAuthCodeRequestUrl;
import static dev.steady.user.fixture.UserFixtures.createUserCreateRequest;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestConfig {

    @Test
    @DisplayName("Account가 존재하면 User를 등록하고 OAuth 인가코드 요청 URL을 반환할 수 있다.")
    void createUser() throws Exception {
        // given
        UserCreateRequest request = createUserCreateRequest();
        URI authCodeRequestUrl = createAuthCodeRequestUrl();
        given(userService.createUser(request)).willReturn(1L);
        given(accountService.registerUser(request.accountId(), 1L)).willReturn(KAKAO);

        // when
        when(oAuthService.getAuthCodeRequestUrlProvider(KAKAO)).thenReturn(authCodeRequestUrl);

        // then
        mockMvc.perform(post("/api/v1/user/profile")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("user-v1-create",
                        resourceDetails().tag("사용자").description("유저 프로필 생성")
                                .responseSchema(Schema.schema("UserCreateResponse")),
                        requestFields(
                                fieldWithPath("accountId").type(NUMBER).description("계정 id"),
                                fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
                                fieldWithPath("positionId").type(NUMBER).description("사용자 포지션"),
                                fieldWithPath("stackIds").type(ARRAY).description("사용자 기술 스택")
                        )
                ))
                .andExpect(header().string("Location", authCodeRequestUrl.toString()));
    }

}
