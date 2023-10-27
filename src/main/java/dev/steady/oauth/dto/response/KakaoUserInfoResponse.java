package dev.steady.oauth.dto.response;

import dev.steady.oauth.domain.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoUserInfoResponse implements OAuthUserInfoResponse {
    
    private Long id;

    @Override
    public Platform getPlatform() {
        return Platform.KAKAO;
    }

    @Override
    public String getPlatformId() {
        return Long.toString(this.id);
    }

}
