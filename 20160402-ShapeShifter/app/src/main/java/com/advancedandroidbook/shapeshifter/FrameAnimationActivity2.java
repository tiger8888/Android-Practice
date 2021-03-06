package com.advancedandroidbook.shapeshifter;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class FrameAnimationActivity2 extends Activity {

	AnimationDrawable mframeAnimation = null;
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.framebyframe_switcher);

		ImageSwitcher switcher = (ImageSwitcher) findViewById(R.id.image_switcher_juggle);
		switcher.setVisibility(View.VISIBLE);

		switcher.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				ImageView iv = new ImageView(FrameAnimationActivity2.this);
				iv.setBackgroundColor(0xFF000000);
				iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				iv.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
				return iv;
			}
		});

		// Handle Start Button
		final Button onButton = (Button) findViewById(R.id.button_start);
		onButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
			}
		});

	}

	private void startAnimation() {
		final ImageSwitcher switcher = (ImageSwitcher) findViewById(R.id.image_switcher_juggle);

		new Thread() {
			final int duration = 500;
			final int imageResourceIds[] = {
					R.drawable.splash1,
					R.drawable.splash2,
					R.drawable.splash3
			};
			int cur = 0;

			@Override
			public void run() {

				while (cur < imageResourceIds.length) {
					synchronized (this) {
						final int curIdx = cur;
						mHandler.post(new Runnable() {
							public void run() {
								switcher.setImageResource(imageResourceIds[curIdx]);
							}
						});

						cur++;

						try {
							wait(duration);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		}.start();

	}

}
