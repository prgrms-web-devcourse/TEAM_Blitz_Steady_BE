package dev.steady.steady.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    @DisplayName("프로모션은 3회만 가능하다.")
    void promotionUseTest() {
        Promotion promotion = new Promotion();
        promotion.use();
        promotion.use();
        promotion.use();
    }

}