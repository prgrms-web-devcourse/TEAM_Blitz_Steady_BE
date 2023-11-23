package dev.steady.review.controller;

import com.epages.restdocs.apispec.Schema;
import dev.steady.global.auth.Authentication;
import dev.steady.global.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.review.fixture.ReviewFixture.createReviewCreateRequest;
import static dev.steady.review.fixture.ReviewFixture.createReviewMyResponse;
import static dev.steady.review.fixture.ReviewFixture.createReviewSwitchResponse;
import static org.mockito.BDDMockito.given;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTestConfig {

    @Test
    @DisplayName("리뷰이 ID와 스테디 ID를 통해 리뷰를 생성할 수 있다.")
    void createReviewTest() throws Exception {
        // given
        var userId = 1L;
        var userInfo = createUserInfo(userId);
        var steadyId = 1L;
        var request = createReviewCreateRequest();
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);
        given(reviewService.createReview(steadyId, request, userInfo)).willReturn(1L);

        // when, then
        mockMvc.perform(post("/api/v1/steadies/{steadyId}/review", steadyId)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("review-v1-create",
                        resourceDetails().tag("리뷰").description("리뷰 생성")
                                .requestSchema(Schema.schema("ReviewCreateRequest")),
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("토큰")
                        ),
                        requestFields(
                                fieldWithPath("revieweeId").type(NUMBER).description("리뷰이 사용자 식별자"),
                                fieldWithPath("cardsId").type(ARRAY).description("카드 식별자 리스트"),
                                fieldWithPath("comment").type(STRING).description("리뷰 코멘트")
                        )
                ))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl(String.format("/api/v1/reviews/%d", steadyId, 1L)));
    }

    @Test
    @DisplayName("리뷰이는 자신이 받은 리뷰의 공개 여부를 설정할 수 있다.")
    void updateReviewIsPublicTest() throws Exception {
        // given
        var userId = 1L;
        var userInfo = createUserInfo(userId);
        var auth = new Authentication(userId);
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);

        var reviewId = 1L;
        var response = createReviewSwitchResponse(true);
        given(reviewService.switchReviewIsPublic(reviewId, userInfo)).willReturn(response);

        // when, then
        mockMvc.perform(patch("/api/v1/reviews/{reviewId}", reviewId)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("review-v1-update",
                        resourceDetails().tag("리뷰").description("리뷰 공개 여부 수정")
                                .responseSchema(Schema.schema("ReviewSwitchRepsonse")),
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("isPublic").type(BOOLEAN).description("수정된 리뷰 공개 여부 상태")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("내가 받은 카드 및 리뷰 목록을 조회할 수 있다.")
    void getOtherUserDetail() throws Exception {
        // given
        var userId = 1L;
        var userInfo = createUserInfo(userId);
        var auth = new Authentication(userId);
        var response = createReviewMyResponse();
        given(jwtResolver.getAuthentication(TOKEN)).willReturn(auth);
        given(reviewService.getMyCardsAndReviews(userInfo)).willReturn(response);

        // when, then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header(AUTHORIZATION, TOKEN)
                )
                .andDo(document("review-v1-get-myAll",
                        resourceDetails().tag("리뷰").description("내가 받은 카드와 리뷰 조회")
                                .responseSchema(Schema.schema("ReviewMyResponse")),
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("userCards[].cardId").type(NUMBER).description("카드 식별자"),
                                fieldWithPath("userCards[].imageUrl").type(STRING).description("카드 이미지 URL"),
                                fieldWithPath("userCards[].count").type(NUMBER).description("사용자가 받은 카드 개수"),
                                fieldWithPath("reviews[].steadyId").type(NUMBER).description("스테디 식별자"),
                                fieldWithPath("reviews[].steadyName").type(STRING).description("스테디 이름"),
                                fieldWithPath("reviews[].reviews[].reviewId").type(NUMBER).description("리뷰 식별자"),
                                fieldWithPath("reviews[].reviews[].comment").type(STRING).description("리뷰 코멘트"),
                                fieldWithPath("reviews[].reviews[].isPublic").type(BOOLEAN).description("리뷰 공개 여부"),
                                fieldWithPath("reviews[].reviews[].createdAt").type(STRING).description("리뷰 생성일")
                        )
                )).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }


    @Test
    @DisplayName("카드 전체 조회 요청을 통해 모든 카드를 가져올 수 있다.")
    void getAllCards() throws Exception {
        var response = createCardsResponse();

        given(reviewService.getAllCards()).willReturn(response);

        mockMvc.perform(get("/api/v1/cards"))
                .andDo(document("review-get-cards",
                                resourceDetails().tag("리뷰").description("카드 전체 조회")
                                        .responseSchema(Schema.schema("CardsResponse")),
                                responseFields(
                                        fieldWithPath("cards[].cardId").type(NUMBER).description("카드 식별자"),
                                        fieldWithPath("cards[].content").type(STRING).description("카드 내용")
                                )
                        )
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
