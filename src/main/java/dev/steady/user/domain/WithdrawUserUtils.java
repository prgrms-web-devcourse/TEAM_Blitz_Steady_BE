package dev.steady.user.domain;

import java.util.UUID;

public class WithdrawUserUtils {

    public static final int BEGIN_INDEX = 0;
    public static final int END_INDEX = 8;

    public static String generateWithdrawName() {
        String uuidStr = UUID.randomUUID().toString();
        return uuidStr.substring(BEGIN_INDEX, END_INDEX);
    }

}
