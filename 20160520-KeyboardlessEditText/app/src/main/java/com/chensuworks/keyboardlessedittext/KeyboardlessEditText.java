package com.chensuworks.keyboardlessedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

public class KeyboardlessEditText extends EditText {

    //private static final Method mShowSoftInputOnFocus = ReflectionUtils.getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);
/*
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //setCursorVisible(true);
        }
    };

    private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            //setCursorVisible(true);
            return false;
        }
    };
*/

    public KeyboardlessEditText(Context context) {
        super(context);
        //initialize();
    }

    public KeyboardlessEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initialize();
    }

    public KeyboardlessEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //initialize();
    }

/*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initialize() {
        //synchronized (this) {
            //setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            //setFocusableInTouchMode(true);
        //}

        //setOnClickListener(mOnClickListener);
        //setOnLongClickListener(mOnLongClickListener);

        //setShowSoftInputOnFocus(false); // This is a hidden method in TextView before LOLLIPOP
        //reflexSetShowSoftInputOnFocus(false);

        //setSelection(getText().length());
    }
*/
/*
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        //hideKeyboard();
    }
*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean ret = super.onTouchEvent(event);
        hideKeyboard();
        return ret;
    }

    @Override
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        //hideKeyboard();
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive(this)) {
            boolean result = imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
            //Toast.makeText(getContext(), result == true ? "true" : "false", Toast.LENGTH_SHORT).show();
        }
    }
/*
    private void reflexSetShowSoftInputOnFocus(boolean show) {
        if (mShowSoftInputOnFocus != null) {
            ReflectionUtils.invokeMethod(mShowSoftInputOnFocus, this, show);
        } else {
            hideKeyboard();
        }
    }
*/

}
