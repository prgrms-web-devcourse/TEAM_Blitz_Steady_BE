package dev.steady.user.domain;

import java.util.UUID;

public class WithdrawUserUtils {

    private WithdrawUserUtils() {
    }

    public static final String WITHDRAW_USER_NAME_PREFIX = "탈퇴한 유저%s";
    public static final int BEGIN_INDEX = 0;
    public static final int END_INDEX = 8;

    public static String generateWithdrawName() {
        String uuidStr = UUID.randomUUID().toString();
        return String.format(WITHDRAW_USER_NAME_PREFIX, uuidStr.substring(BEGIN_INDEX, END_INDEX));
    }

}
