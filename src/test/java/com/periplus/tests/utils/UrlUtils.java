package com.periplus.tests.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    private static final Pattern PRODUCT_ID_PATTERN = Pattern.compile("/p/(\\d+)");

    public static String extractProductIdFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        Matcher matcher = PRODUCT_ID_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
