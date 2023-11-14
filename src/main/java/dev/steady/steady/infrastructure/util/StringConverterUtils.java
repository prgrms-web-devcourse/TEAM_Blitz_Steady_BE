package dev.steady.steady.infrastructure.util;

public class StringConverterUtils {

    public static String lowercaseFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

}
