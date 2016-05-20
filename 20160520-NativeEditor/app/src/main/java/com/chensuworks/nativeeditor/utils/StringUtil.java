package com.chensuworks.nativeeditor.utils;

public class StringUtil {

    public static String removeChar(String texts, int index) {
        if (index < 0 || index >= texts.length()) {
            return null;
        }

        if (index == 0) {
            return texts.substring(1);
        }

        return texts.substring(0, index) + texts.substring(index + 1);
    }
    
}
