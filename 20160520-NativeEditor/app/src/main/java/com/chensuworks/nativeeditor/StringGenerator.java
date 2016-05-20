package com.chensuworks.nativeeditor;

public class StringGenerator {

    public static String generateMultipleLines(int lineCount, int charCount) {
        String texts = "";

        for (int i = 0; i < lineCount; i++) {
            texts += "LINE " + i + ": " + StringGenerator.generateOneLongLine(charCount) + "\n";
        }

        return texts;
    }

    public static String generateOneLongLine(int charCount) {
        String texts = "";

        for (int i = 0; i < charCount; i++) {
            texts += "superduper" + i + " ";
        }

        return texts;
    }

}
