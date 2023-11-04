package dev.steady.auth.service.Fixtures;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.Platform;

public class AccountFixture {

    public static Account createAccount() {
        return new Account(Platform.KAKAO, "111111");
    }

}
