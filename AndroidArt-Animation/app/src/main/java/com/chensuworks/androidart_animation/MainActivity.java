package com.chensuworks.androidart_animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by chensu on 7/27/16.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button1 = (Button) findViewById(R.id.button1);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_test);
        button1.startAnimation(animation);

        Button button2 = (Button) findViewById(R.id.button2);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(5000);
        button2.startAnimation(alphaAnimation);
    }
}
