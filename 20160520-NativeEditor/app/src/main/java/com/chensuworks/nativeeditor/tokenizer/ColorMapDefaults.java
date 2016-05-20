package com.chensuworks.nativeeditor.tokenizer;

import android.graphics.Color;

import java.util.HashMap;

public class ColorMapDefaults {

    public static final HashMap<Integer, String> typeMapDefaults;
    public static final HashMap<Integer, Integer> colorMapDefaults;
    public static final HashMap<Integer, Integer> HTMLColorMapDefaults;

    static {
        typeMapDefaults = new HashMap<Integer, String>();

        typeMapDefaults.put(122, "error"); // syntax error
        typeMapDefaults.put(105, "comment");
        typeMapDefaults.put(106, "comment");
        typeMapDefaults.put(107, "comment");
        typeMapDefaults.put(108, "comment");
        typeMapDefaults.put(109, "comment");
        typeMapDefaults.put(110, "comment");
        typeMapDefaults.put(44, "string");
        typeMapDefaults.put(45, "string");
        typeMapDefaults.put(333, "keyword");
        typeMapDefaults.put(337, "invalid"); // unterminated strings
        typeMapDefaults.put(338, "system");
        typeMapDefaults.put(339, "plain");
    }

    static {
        colorMapDefaults = new HashMap<Integer, Integer>();

        colorMapDefaults.put(122, Color.rgb(255, 0, 0));
        colorMapDefaults.put(105, Color.rgb(34, 139, 34));
        colorMapDefaults.put(106, Color.rgb(34, 139, 34));
        colorMapDefaults.put(107, Color.rgb(34, 139, 34));
        colorMapDefaults.put(108, Color.rgb(34, 139, 34));
        colorMapDefaults.put(109, Color.rgb(34, 139, 34));
        colorMapDefaults.put(110, Color.rgb(34, 139, 34));
        colorMapDefaults.put(44, Color.rgb(160, 32, 240));
        colorMapDefaults.put(45, Color.rgb(160, 32, 240));
        colorMapDefaults.put(333, Color.rgb(0, 0, 255));
        colorMapDefaults.put(337, Color.rgb(179, 0, 0));
        colorMapDefaults.put(338, Color.rgb(179, 140, 0));
        colorMapDefaults.put(339, Color.rgb(0, 0, 0));
    }

    static {
        HTMLColorMapDefaults = new HashMap<Integer, Integer>();

        HTMLColorMapDefaults.put(122, Color.rgb(255, 0, 0));
        HTMLColorMapDefaults.put(105, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(106, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(107, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(108, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(109, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(110, Color.rgb(34, 139, 34));
        HTMLColorMapDefaults.put(44, Color.rgb(160, 32, 240));
        HTMLColorMapDefaults.put(45, Color.rgb(160, 32, 240));
        HTMLColorMapDefaults.put(333, Color.rgb(0, 0, 255));
        HTMLColorMapDefaults.put(337, Color.rgb(179, 0, 0));
        HTMLColorMapDefaults.put(338, Color.rgb(179, 140, 0));
        HTMLColorMapDefaults.put(339, Color.rgb(0, 0, 0));
    }
}
