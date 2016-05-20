package com.chensuworks.keyboardlessedittext;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;
import org.xwalk.core.XWalkView;

/**
 * https://github.com/danialgoodwin/android-widget-keyboardless-edittext
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private View mDot;
    private View mDash;
    private View mBackspace;

    private WebView mWebView;
    private KeyboardlessWebView mKeyboardlessWebView;

    private boolean keyboardListenerAttached = false;
    private ViewGroup rootLayout;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);

            if (heightDiff <= contentViewTop) {
                onHideKeyboard();
/*
                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
*/
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard(keyboardHeight);
/*
                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
*/
            }
        }
    };

    protected void onShowKeyboard(int keyboardHeight) {
        //Toast.makeText(getApplicationContext(), "onShowKeyboard", Toast.LENGTH_SHORT).show();

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }

    }

    protected void onHideKeyboard() {
        //Toast.makeText(getApplicationContext(), "onHideKeyboard", Toast.LENGTH_SHORT).show();
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenerAttached) {
            return;
        }

        rootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenerAttached = true;
    }

    protected void attachSuperKeyboardListeners() {
        rootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);

        final SoftKeyboard softKeyboard = new SoftKeyboard(rootLayout, imm);

        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {
            @Override
            public void onSoftKeyboardHide() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "onSoftKeyboardHide()", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onSoftKeyboardShow() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "onSoftKeyboardShow()", Toast.LENGTH_SHORT).show();
                        softKeyboard.closeSoftKeyboard();
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //attachKeyboardListeners();
        //attachSuperKeyboardListeners(); // Bad solution. Crash. If keep clicking in EditText, keyboard will finally show up.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
/*
        mEditText = (EditText) findViewById(R.id.keyboardless_edit_text);

        mDot = findViewById(R.id.dot_button);
        mDash = findViewById(R.id.dash_button);
        mBackspace = findViewById(R.id.backspace_button);

        mDot.setOnClickListener(this);
        mDash.setOnClickListener(this);
        mBackspace.setOnClickListener(this);
*/
/*
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://www.google.com");
*/
        XWalkView xWalkWebView = (XWalkView) findViewById(R.id.crosswalk);
        xWalkWebView.load("https://www.google.com", null);

/*
        mKeyboardlessWebView = (KeyboardlessWebView) findViewById(R.id.keyboardless_webview);
        mKeyboardlessWebView.loadUrl("https://www.google.com");
*/
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (keyboardListenerAttached) {
            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }
    @Override
    public void onClick(View v) {
/*
        switch (v.getId()) {
            case R.id.dot_button:
                pressKey(KeyEvent.KEYCODE_PERIOD);
                return;
            case R.id.dash_button:
                pressKey(KeyEvent.KEYCODE_MINUS);
                return;
            case R.id.backspace_button:
                pressKey(KeyEvent.KEYCODE_DEL);
                return;
        }
*/
    }
    private void pressKey(int keyCode) {
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        mEditText.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
