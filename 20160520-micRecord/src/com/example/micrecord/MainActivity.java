package com.example.micrecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.PrivateCredentialPermission;




import android.R.string;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
	private Button startButton;
	private Button endButton;
	
	private SoundRecorder mSoundRecorder;
	private SensorManager mSensorManager;
	private Sensor mMagSensor;
	private Sensor mAccSensor;
	private Sensor mGravitySensor;
	private Sensor mRotSensor;
	private MyApp mData;
	
	private TextView gravityValueText;
	private String gravityValueString;
	private TextView disValueText;
	private String disValueString;
	
	
	private Timer mTimer;
	private TimerTask task;
	private int samplerate = 50;
	
	private LogData logData;
	SimpleDateFormat sDateFormat;
	String time, filename;
	File svFile;
	
	private long timeStamp;
    private final float[] mRotationMatrix = new float[16];
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startButton = (Button) this.findViewById(R.id.button1);
		endButton = (Button) this.findViewById(R.id.button2);
		
		mSoundRecorder = new SoundRecorder();		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		gravityValueText=(TextView) findViewById(R.id.light);
		disValueText=(TextView) findViewById(R.id.dis);
		
		mData = (MyApp) getApplicationContext();
		
		initSensors();
		
	    sDateFormat = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");
		logData = new LogData();
		
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSoundRecorder.startRecording();
				time = sDateFormat.format(new java.util.Date());
                filename = time + ".txt";
                svFile = logData.createFile(filename);
       			timerset();
       			Log.d("My::","Start button listener.");
			}
		});
		
		endButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mSoundRecorder.getStatus()) {
					mSoundRecorder.stopRecording();
				}
				if (mTimer != null) {
					mTimer.cancel();	
				}

				Log.d("My::","End button listener.");
			}
		});
	}
	
	private void initSensors() {
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)==null) {
			Toast.makeText(this, "No Mag Sensor", Toast.LENGTH_SHORT).show();
		} else {
			mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		}
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null) {
			Toast.makeText(this, "No Acc Sensor", Toast.LENGTH_SHORT).show();
		} else {
			mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)==null) {
			Toast.makeText(this, "No Gravity Sensor", Toast.LENGTH_SHORT).show();
		} else {
			mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		}
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)==null) {
			Toast.makeText(this, "No Dis Sensor", Toast.LENGTH_SHORT).show();
		} else {
			mRotSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		}
        mRotationMatrix[ 0] = 1;
        mRotationMatrix[ 4] = 1;
        mRotationMatrix[ 8] = 1;
        mRotationMatrix[12] = 1;
	}
	
	protected void onResume() {
		super.onResume();
	    mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_FASTEST);
	    mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_FASTEST);
	    mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
	    mSensorManager.registerListener(this, mRotSensor, SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor sensor = event.sensor;
		float[] rotationResult = new float[3];
		
		if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			float[] magValue = new float[3];
			magValue = event.values.clone();
			mData.setMag(magValue);
			//magResult = "mag_x: " + magValue[0] + ";" + "\n" + "mag_y: " + magValue[1] + ";" + "\n" + "mag_z: " + magValue[2] + ";";
			//magResult = magValue[0] + "," + magValue[1] + "," + magValue[2] + ";";
		    //disValueString="Mag: " + magValue[0] + ";\n" + magValue[1] + ";\n" + magValue[2] + ";"  ;
			//disValueText.setText(disValueString);
		} else if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float[] accValue = new float[3];
			accValue = event.values.clone();
			mData.setAcc(accValue);
		} else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
			float[] graValue = new float[3];
			graValue = event.values.clone();
			mData.setGra(graValue);
		    gravityValueString="Gravity Sensor: " + graValue[0];
			gravityValueText.setText(gravityValueString);
		} else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//            SensorManager.getRotationMatrixFromVector(mRotationMatrix , event.values);
			
			float[] rotValue = new float[3];
			float[] quat = new float[4];
			Quaternion q2 = new Quaternion();
			float [] rotationMatrix2 = new float[9];

            System.arraycopy(event.values, 0, rotValue, 0, 3);
            SensorManager.getQuaternionFromVector(quat , event.values);
            q2.w = quat[0]; q2.x = quat[1]; q2.y = quat[2]; q2.z = quat[3];
            rotationMatrix2 = getRotationMatrixFromQuaternion(q2);
            rotationResult =  matrixMultiplication(mData.getMag(),rotationMatrix2);
		}

//		float[] R = new float[16];
//		float[] I = new float[16];
//		float[] oV = new float[3];
		
		if (mData.getAcc()!=null && mData.getGra()!=null) {
//			SensorManager.getRotationMatrix(R, I, mData.getAcc(), mData.getGra());
//			SensorManager.getOrientation(R, oV);
			Log.d("My::", "X: " + rotationResult[0]);
	        Log.d("My::", "Y: " + rotationResult[1]);
	        Log.d("My::", "Z: " + rotationResult[2]);
	        
		    disValueString=rotationResult[0] + ";\n" + rotationResult[1] + ";\n" + rotationResult[2] + ";"  ;
			disValueText.setText(disValueString);
			
//			Log.d("My::", "rot mat:\n" + mRotationMatrix[0] + "," + mRotationMatrix[1] + "," + mRotationMatrix[2] + "," + mRotationMatrix[3] + ";"
//					 				   + mRotationMatrix[4] + "," + mRotationMatrix[5] + "," + mRotationMatrix[6] + "," + mRotationMatrix[7] + ";"
//					 				   + mRotationMatrix[8] + "," + mRotationMatrix[9] + "," + mRotationMatrix[10] + "," + mRotationMatrix[11] + ";"
//					 				   + mRotationMatrix[12] + "," + mRotationMatrix[13] + "," + mRotationMatrix[14] + "," + mRotationMatrix[15] + ".\n"
//					 	+ "RRR mat:\n" + R[0] + "," + R[1] + "," + R[2] + "," + R[3] + ";"
//							 		   + R[4] + "," + R[5] + "," + R[6] + "," + R[7] + ";"
//							 		   + R[8] + "," + R[9] + "," + R[10] + "," + R[11] + ";"
//							 		   + R[12] + "," + R[13] + "," + R[14] + "," + R[15] + ".\n");
		}
		timeStamp = System.currentTimeMillis();
		mData.setTime(timeStamp);
	}
	
    private float[] getRotationMatrixFromQuaternion(Quaternion q22) {
        // TODO Auto-generated method stub
        float [] q = new float[4];
        float [] result = new float[9];
        q[0] = q22.w;
        q[1] = q22.x;
        q[2] = q22.y;
        q[3] = q22.z;

        result[0] = q[0]*q[0] + q[1]*q[1] - q[2]*q[2] -q[3]*q[3];
        result[1] = 2 * (q[1]*q[2] - q[0]*q[3]);
        result[2] = 2 * (q[1]*q[3] + q[0]*q[2]);

        result[3] = 2 * (q[1]*q[2] + q[0]*q[3]);
        result[4] = q[0]*q[0] - q[1]*q[1] + q[2]*q[2] - q[3]*q[3];
        result[5] = 2 * (q[2]*q[3] - q[0]*q[1]);

        result[7] = 2 * (q[2]*q[3] + q[0]*q[1]);
        result[6] = 2 * (q[1]*q[3] - q[0]*q[2]);
        result[8] = q[0]*q[0] - q[1]*q[1] - q[2]*q[2] + q[3]*q[3];

        return result;
    }

    private float[] matrixMultiplication(float[] A, float[] B) {
        float[] result = new float[3];

        result[0] = A[0] * B[0] + A[1] * B[1] + A[2] * B[2];
        result[1] = A[0] * B[3] + A[1] * B[4] + A[2] * B[5];
        result[2] = A[0] * B[6] + A[1] * B[7] + A[2] * B[8];

        return result;
    }
	
	private void timerset() {
		
        task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
     			mData.setTime(System.currentTimeMillis());
				float[] magValue = new float[3];
				float[] accValue = new float[3];
				float[] disValue = new float[1];
				float[] lightValue = new float[1];
				magValue = mData.getMag();
				accValue = mData.getAcc();
				disValue = mData.getDis();
				lightValue = mData.getGra();
				
				String result = mData.getTime() + "," + accValue[0] + "," + accValue[1] + "," 
				                + accValue[2] + "," + magValue[0] + "," + magValue[1] + "," + magValue[2] + ","
				                + disValue[0] + "," + lightValue[0];
				logData.saveData(svFile, result);
			}
		};
		
		mTimer = new Timer();
		mTimer.schedule(task,0,samplerate);
	}

	protected void onStart() {
		super.onStart();
	}
	
	protected void onPause() {
		  super.onPause();
		  mSensorManager.unregisterListener(this);
		}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//mSoundRecorder.stopRecording();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
