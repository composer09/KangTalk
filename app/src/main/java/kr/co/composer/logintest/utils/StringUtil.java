package kr.co.composer.logintest.utils;

/**
 * Created by composer10 on 2015. 8. 26..
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
