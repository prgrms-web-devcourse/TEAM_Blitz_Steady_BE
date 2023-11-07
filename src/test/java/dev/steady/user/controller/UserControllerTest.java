package dev.steady.user.controller;

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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestConfig {

    @Test
    @DisplayName("Account가 존재하면 User를 등록하고 OAuth 인가코드 요청 URL을 반환할 수 있다.")
    void createUser() throws Exception {
        // given
        var request = createUserCreateRequest();
        var authCodeRequestUrl = createAuthCodeRequestUrl();
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

    @Test
    @DisplayName("이미 존재하는 nickname에 대해서 true를 반환할 수 있다.")
    void existsByNickname() throws Exception {
        // given
        var nickname = "닉네임";
        var response = new UserNicknameExistResponse(true);
        given(userService.existsByNickname(nickname)).willReturn(response);
        // when, then
        mockMvc.perform(get("/api/v1/user/profile/exist")
                        .queryParam("nickname", nickname))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-v1-check-nickname", resourceDetails().tag("사용자")
                                .description("유저 닉네임 중복 검사")
                                .responseSchema(Schema.schema("UserCheckNicknameResponse")),
                        queryParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseBody()
                ))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

}
