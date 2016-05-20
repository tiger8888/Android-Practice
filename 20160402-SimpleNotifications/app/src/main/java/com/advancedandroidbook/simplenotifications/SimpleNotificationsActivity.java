package com.advancedandroidbook.simplenotifications;

import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class SimpleNotificationsActivity extends Activity {

	// Notification identifiers
	private static final int NOTIFY_1 = 0x1001;
	private static final int NOTIFY_2 = 0x1002;
	private static final int NOTIFY_3 = 0x1003;
	private static final int NOTIFY_4 = 0x1004;
	private static final int NOTIFY_5 = 0x1005;
	private static final int NOTIFY_6 = 0x1006;

	private NotificationManager notificationManager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		hookUpListenerForButtonText();
		hookUpListenerForButtonVibrate();
		hookUpListenerForButtonLED();
		hookUpListenerForButtonNoise();
		hookUpListenerForButtonRemoteView();
		hookUpListenerForButtonExpand();
	}

	private void hookUpListenerForButtonText() {

		final Counter counter = new Counter();

		Button notifyTextButton = (Button) findViewById(R.id.notifyText);

		notifyTextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setTicker("Hello!");
				notifyBuilder.setWhen(System.currentTimeMillis());
				notifyBuilder.setAutoCancel(true);
				notifyBuilder.setContentTitle("Hi there!");
				notifyBuilder.setContentText("This is even more text.");

				notifyBuilder.setNumber(counter.getCount());
				counter.increment();

				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_1, notification);
			}
		});

	}

	private void hookUpListenerForButtonVibrate() {

		Button notifyVibrateButton = (Button) findViewById(R.id.notifyVibrate);

		notifyVibrateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(android.R.drawable.stat_notify_chat);
				notifyBuilder.setTicker("Vibrate!");
				notifyBuilder.setWhen(System.currentTimeMillis());
				notifyBuilder.setAutoCancel(true);
				notifyBuilder.setContentTitle("Bzzt!");
				notifyBuilder.setContentText("This vibrated your phone.");

				notifyBuilder.setVibrate(new long[]{200, 200, 600, 600, 600,
						200, 200, 600, 600, 200, 200, 200, 200, 600, 200, 200,
						600, 200, 200, 600, 600, 200, 600, 200, 600, 600, 200,
						200, 200, 600, 600, 200, 200, 200, 200, 600,});


				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_2, notification);

				// more than one way to vibrate
				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(500);
			}
		});
	}

	private void hookUpListenerForButtonLED() {

		final Counter counter = new Counter();

		Button notify3 = (Button) findViewById(R.id.notifyLED);

		notify3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setTicker("Lights!");
				notifyBuilder.setWhen(System.currentTimeMillis());
				notifyBuilder.setAutoCancel(true);
				notifyBuilder.setContentTitle("Bright!");
				notifyBuilder.setContentText("This lit up your phone.");

				notifyBuilder.setNumber(counter.getCount());

				int argb;
				int onMs;
				int offMs;

				if (counter.getCount() < 2) {
					argb = Color.GREEN;
					onMs = 1000;
					offMs = 1000;
				} else if (counter.getCount() < 3) {
					argb = Color.BLUE;
					onMs = 750;
					offMs = 750;
				} else if (counter.getCount() < 4) {
					argb = Color.WHITE;
					onMs = 500;
					offMs = 500;
				} else {
					argb = Color.RED;
					onMs = 50;
					offMs = 50;
				}

				counter.increment();

				notifyBuilder.setLights(argb, onMs, offMs);

				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_3, notification);
			}
		});
	}

	private void hookUpListenerForButtonNoise() {
		Button notifyNoiseButton = (Button) findViewById(R.id.notifyNoise);

		notifyNoiseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setTicker("Noise!");
				notifyBuilder.setWhen(System.currentTimeMillis());
				notifyBuilder.setAutoCancel(true);
				notifyBuilder.setContentTitle("Wow!");
				notifyBuilder.setContentText("This made your phone noisy.");

				notifyBuilder.setSound(
						Uri.parse("android.resource://com.advancedandroidbook.simplenotifications/" + R.raw.fallbackring),
						AudioManager.STREAM_NOTIFICATION
				);

				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_4, notification);
			}
		});
	}

	private void hookUpListenerForButtonRemoteView() {
		Button notifyRemoteButton = (Button) findViewById(R.id.notifyRemote);

		notifyRemoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setTicker("Remote!");
				notifyBuilder.setWhen(System.currentTimeMillis());
				notifyBuilder.setAutoCancel(true);

				RemoteViews remote = new RemoteViews(getPackageName(), R.layout.remote);
				remote.setTextViewText(R.id.text1, "Big text here!");
				remote.setTextViewText(R.id.text2, "Red text down here!");
				notifyBuilder.setContent(remote);

				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_5, notification);
			}
		});
	}

	private void hookUpListenerForButtonExpand() {
		Button notifyExpandButton = (Button) findViewById(R.id.notifyExpand);

		notifyExpandButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext());

				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setTicker("Expand!");
				notifyBuilder.setWhen(System.currentTimeMillis());

				notifyBuilder.setAutoCancel(true);
				notifyBuilder.setContentTitle("Expanding!");
				notifyBuilder.setContentText("This is even more text.");

				Intent toLaunch = new Intent(SimpleNotificationsActivity.this, SimpleNotificationsActivity.class);
				notifyBuilder.setContentIntent(PendingIntent.getActivity(SimpleNotificationsActivity.this, 0, toLaunch, 0));

				notifyBuilder.setStyle(new NotificationCompat.BigTextStyle()
						.bigText("This is a really long message that is used "
								+ "for expanded notifications in the status bar"));

				PendingIntent action1 = PendingIntent.getActivity(getApplicationContext(), 0, toLaunch, 0);
				PendingIntent action2 = PendingIntent.getActivity(getApplicationContext(), 0, toLaunch, 0);

				notifyBuilder.addAction(R.drawable.ic_launcher, "Action 1", action1);
				notifyBuilder.addAction(R.drawable.ic_launcher, "Action 2", action2);

				Notification notification = notifyBuilder.build();
				notificationManager.notify(NOTIFY_6, notification);
			}
		});
	}

	private final class Counter {
		private int count;

		public Counter() {
			setCount(1);
		}

		public int increment() {
			this.setCount(this.getCount() + 1);
			return this.getCount();
		}

		public int getCount() {
			return count;
		}

		private void setCount(int count) {
			this.count = count;
		}
	}

}