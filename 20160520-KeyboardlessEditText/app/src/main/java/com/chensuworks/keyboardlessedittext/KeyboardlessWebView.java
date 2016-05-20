package com.chensuworks.keyboardlessedittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Toast;

public class KeyboardlessWebView extends WebView {

    public KeyboardlessWebView(Context context) {
        super(context);
        //initialize();
    }

    public KeyboardlessWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initialize();
    }

    public KeyboardlessWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //initialize();
    }

    private void initialize() {
        synchronized (this) {
            setFocusableInTouchMode(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        forceHide();

/*
        final boolean ret = super.onTouchEvent(event);
        //hideKeyboard();

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(getWindowToken(), 0);


        return ret;
*/
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        forceHide();

        //InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);

        //hideKeyboard();
        //((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
/*
        try {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            final IBinder token = getWindowToken();
            imm.setInputMethod(token, "com.chensuworks.keyboardlessedittext/.SimpleIME");

        } catch (Throwable t) {
            t.printStackTrace();
        }
*/

        //InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromInputMethod(getWindowToken(), 0);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        forceHide();

        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive(this)) {
            IBinder token = getApplicationWindowToken();
            boolean result = imm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            Toast.makeText(getContext(), result == true ? "true" : "false", Toast.LENGTH_SHORT).show();
            Log.d("WEBVIEW", "hideKeyboard() called!");
        }
    }

    public void forceHide() {
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

/*
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.actionLabel = "";
        outAttrs.hintText = "";
        outAttrs.initialCapsMode = 0;
        outAttrs.initialSelEnd = outAttrs.initialSelStart = -1;
        outAttrs.label = "";
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI;
        outAttrs.inputType = InputType.TYPE_NULL;

        return new BaseInputConnection(this, false);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }
*/

}
