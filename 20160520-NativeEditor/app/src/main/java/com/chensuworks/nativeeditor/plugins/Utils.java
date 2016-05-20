package com.chensuworks.nativeeditor.plugins;

public class Utils {

    public static boolean isWhitespace(String text) {
        if (text == null) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if ((Character.isWhitespace(text.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

}
