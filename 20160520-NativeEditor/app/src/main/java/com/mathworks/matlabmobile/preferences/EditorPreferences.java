package com.mathworks.matlabmobile.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EditorPreferences {

    public static final String EDITOR_PREFS_FILENAME = "EDITOR_PREFS";

    public static final String KEY_USE_SYNTAX_HIGHLIGHT = "UseSyntaxHighlight";
    public static final boolean DEFAULT_USE_SYNTAX_HIGHLIGHT = true;

    public static final String KEY_USE_AUTO_INDENT = "UseAutoIndent";
    public static final boolean DEFAULT_USE_AUTO_INDENT = true;

    public static final String KEY_USE_PAREN_MATCH = "UseParenMatch";
    public static final boolean DEFAULT_USE_PAREN_MATCH = true;

    public static final String KEY_MAX_LINE_NUMBER = "MaxLineNumber";
    public static final int DEFAULT_MAX_LINE_NUMBER = 100;

    public static final String KEY_USE_NOTEPAD_STYLE_UNDERLINE = "UseNotepadStyleUnderline";
    public static final boolean DEFAULT_USE_NOTEPAD_STYLE_UNDERLINE = false;

    public static final String KEY_USE_LINE_HIGHLIGHT = "UseLineHighlight";
    public static final boolean DEFAULT_USE_LINE_HIGHLIGHT = false;


    public static void loadEditorPreferences(Context context, EditorSettings editorSettings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        editorSettings.setUseSyntaxHighlight(sharedPreferences.getBoolean(KEY_USE_SYNTAX_HIGHLIGHT, DEFAULT_USE_SYNTAX_HIGHLIGHT));
        editorSettings.setUseAutoIndent(sharedPreferences.getBoolean(KEY_USE_AUTO_INDENT, DEFAULT_USE_AUTO_INDENT));
        editorSettings.setUseParenMatch(sharedPreferences.getBoolean(KEY_USE_PAREN_MATCH, DEFAULT_USE_PAREN_MATCH));
        editorSettings.setMaxLineNumber(sharedPreferences.getInt(KEY_MAX_LINE_NUMBER, DEFAULT_MAX_LINE_NUMBER));
        editorSettings.setUseNotepadStyleUnderline(sharedPreferences.getBoolean(KEY_USE_NOTEPAD_STYLE_UNDERLINE, DEFAULT_USE_NOTEPAD_STYLE_UNDERLINE));
        editorSettings.setUseLineHighlight(sharedPreferences.getBoolean(KEY_USE_LINE_HIGHLIGHT, DEFAULT_USE_LINE_HIGHLIGHT));
    }

    public static void saveEditorPreferences(Context context, EditorSettings editorSettings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(KEY_USE_SYNTAX_HIGHLIGHT, editorSettings.getUseSyntaxHighlight());
        editor.putBoolean(KEY_USE_AUTO_INDENT, editorSettings.getUseAutoIndent());
        editor.putBoolean(KEY_USE_PAREN_MATCH, editorSettings.getUseParenMatch());
        editor.putInt(KEY_MAX_LINE_NUMBER, editorSettings.getMaxLineNumber());
        editor.putBoolean(KEY_USE_NOTEPAD_STYLE_UNDERLINE, editorSettings.getUseNotepadStyleUnderline());
        editor.putBoolean(KEY_USE_LINE_HIGHLIGHT, editorSettings.getUseLineHighlight());
        editor.commit();
    }

    public static void enableLineHighlight(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USE_LINE_HIGHLIGHT, true);
        editor.commit();
    }

    public static void flipLineHighlight(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USE_LINE_HIGHLIGHT, !sharedPreferences.getBoolean(KEY_USE_LINE_HIGHLIGHT, DEFAULT_USE_LINE_HIGHLIGHT));
        editor.commit();
    }

    public static void flipUnderline(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USE_NOTEPAD_STYLE_UNDERLINE, !sharedPreferences.getBoolean(KEY_USE_NOTEPAD_STYLE_UNDERLINE, DEFAULT_USE_NOTEPAD_STYLE_UNDERLINE));
        editor.commit();
    }

    public static void incrementMaxLineNumberBy10(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        int maxLineNumber = sharedPreferences.getInt(KEY_MAX_LINE_NUMBER, DEFAULT_MAX_LINE_NUMBER);
        maxLineNumber += 10;
        editor.putInt(KEY_MAX_LINE_NUMBER, maxLineNumber);
        editor.commit();
    }

    public static int getMaxLineNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EDITOR_PREFS_FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MAX_LINE_NUMBER, DEFAULT_MAX_LINE_NUMBER);
    }

    public static class EditorSettings {

        private boolean mUseSyntaxHighlight;
        private boolean mUseAutoIndent;
        private boolean mUseParenMatch;
        private int mMaxLineNumber;
        private boolean mUseNotepadStyleUnderline;
        private boolean mUseLineHighlight;

        public boolean getUseSyntaxHighlight() {
            return mUseSyntaxHighlight;
        }

        public void setUseSyntaxHighlight(boolean mUseSyntaxHighlight) {
            this.mUseSyntaxHighlight = mUseSyntaxHighlight;
        }

        public boolean getUseAutoIndent() {
            return mUseAutoIndent;
        }

        public void setUseAutoIndent(boolean mUseAutoIndent) {
            this.mUseAutoIndent = mUseAutoIndent;
        }

        public boolean getUseParenMatch() {
            return mUseParenMatch;
        }

        public void setUseParenMatch(boolean mUseParenMatch) {
            this.mUseParenMatch = mUseParenMatch;
        }
        public int getMaxLineNumber() {
            return mMaxLineNumber;
        }

        public void setMaxLineNumber(int mMaxLineNumber) {
            this.mMaxLineNumber = mMaxLineNumber;
        }

        public boolean getUseNotepadStyleUnderline() {
            return mUseNotepadStyleUnderline;
        }

        public void setUseNotepadStyleUnderline(boolean mUseNotepadStyleUnderline) {
            this.mUseNotepadStyleUnderline = mUseNotepadStyleUnderline;
        }

        public boolean getUseLineHighlight() {
            return mUseLineHighlight;
        }

        public void setUseLineHighlight(boolean mUseLineHighlight) {
            this.mUseLineHighlight = mUseLineHighlight;
        }

    }

}
