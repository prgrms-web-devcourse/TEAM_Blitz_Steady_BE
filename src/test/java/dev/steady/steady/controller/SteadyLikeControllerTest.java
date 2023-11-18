package dev.steady.steady.controller;

import com.epages.restdocs.apispec.Schema;
import dev.steady.global.auth.Authentication;
import dev.steady.global.auth.UserInfo;
import dev.steady.global.config.ControllerTestConfig;
import dev.steady.steady.dto.response.SteadyLikeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SteadyLikeControllerTest extends ControllerTestConfig {

    @DisplayName("스테디에 좋아요 요청을 보내면 현재 좋아요 상태를 반환한다.")
    @Test
    void updateLikeStatusTest() throws Exception {
        //given
        Long userId = 1L;
        Long steadyId = 1L;
        UserInfo userInfo = new UserInfo(1L);
        SteadyLikeResponse response = new SteadyLikeResponse(1, true);
        when(jwtResolver.getAuthentication(TOKEN)).thenReturn(new Authentication(userId));
        when(steadyLikeService.updateSteadyLike(steadyId, userInfo)).thenReturn(response);

        //when
        //then
        mockMvc.perform(patch("/api/v1/steadies/{steadyId}/like", 1L)
                        .header(AUTHORIZATION, TOKEN))
                .andDo(document("steady-like",
                                resourceDetails().tag("스테디").description("스테디 좋아요")
                                        .responseSchema(Schema.schema("SteadyLikeResponse")),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("토큰")
                                ),
                                responseFields(
                                        fieldWithPath("likeCount").type(NUMBER).description("좋아요 수"),
                                        fieldWithPath("isLike").type(BOOLEAN).description("본인의 좋아요 상태")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
