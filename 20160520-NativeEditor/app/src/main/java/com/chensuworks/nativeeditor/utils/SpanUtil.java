package com.chensuworks.nativeeditor.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

public class SpanUtil {

    public static void underlinePair(Editable editable, int leftPos, int rightPos) {
        // leftPos must be smaller than rightPos
        if (leftPos == -1 || rightPos == -1 || leftPos >= rightPos) {
            return;
        }

        underline(editable, leftPos);
        underline(editable, rightPos);
    }

    private static void underline(Editable editable, int index) {
        editable.setSpan(new UnderlineSpan(), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void removeUnderlinePair(Editable editable, int leftPos, int rightPos) {
        // leftPos must be smaller than rightPos
        if (leftPos == -1 || rightPos == -1 || leftPos >= rightPos) {
            return;
        }

        removeUnderline(editable, leftPos);
        removeUnderline(editable, rightPos);
    }

    private static void removeUnderline(Editable editable, int index) {
        UnderlineSpan[] spans = editable.getSpans(index, index + 1, UnderlineSpan.class);
        for (UnderlineSpan span : spans) {
            editable.removeSpan(span);
        }
    }

    public static void addForegroundColor(Editable editable, int start, int end, Integer color) {
        // start must be smaller than end
        if (start == -1 || end == -1 || start >= end) {
            return;
        }

        editable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void removeForegroundColor(Editable editable, int start, int end) {
        // start must be smaller than end
        if (start == -1 || end == -1 || start >= end) {
            return;
        }

        ForegroundColorSpan[] spans = editable.getSpans(start, end, ForegroundColorSpan.class);
        for (ForegroundColorSpan span : spans) {
            editable.removeSpan(span);
        }
    }

}
