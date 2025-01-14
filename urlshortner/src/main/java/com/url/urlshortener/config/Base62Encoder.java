package com.url.urlshortener.config;

public class Base62Encoder {

    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encode(long value) {
        StringBuilder result = new StringBuilder();
        while (value > 0) {
            int remainder = (int) (value % 62);
            result.append(BASE62_CHARS.charAt(remainder));
            value /= 62;
        }
        return result.reverse().toString();
    }

    public static String generate7CharBase62(long number) {
        String base62Id = encode(number);
        return String.format("%7s", base62Id).replace(' ', '0');
    }
}
