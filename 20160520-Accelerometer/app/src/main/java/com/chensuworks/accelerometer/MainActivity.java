package com.chensuworks.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private TextView mGravityView;
    private TextView mGeomagneticView;
    private TextView mRotationMatrix1;

    private TextView mRotationVectorView;
    private TextView mRotationMatrix2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mGravityView = (TextView) findViewById(R.id.gravity);
        mGeomagneticView = (TextView) findViewById(R.id.geomagnetic);
        mRotationVectorView = (TextView) findViewById(R.id.rotationVector);

        mRotationMatrix1 = (TextView) findViewById(R.id.rotationMatrix1);
        mRotationMatrix2 = (TextView) findViewById(R.id.rotationMatrix2);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] rotationMatrix1 = new float[9];
        float[] inclinationMatrix = new float[9];
        float[] gravity = new float[3];
        float[] geomagnetic = new float[3];

        float[] rotationMatrix2 = new float[9];
        float[] rotationVector = new float[3];

        float[] geomagneticGlobe = new float[3];
        float[] rotationVectorGlobe = new float[3];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = getGravity(event);
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = getGeomagnetic(event);
        }

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            rotationVector = getRotationVector(event);
        }

        SensorManager.getRotationMatrix(rotationMatrix1, inclinationMatrix, gravity, geomagnetic);
        SensorManager.getRotationMatrixFromVector(rotationMatrix2, rotationVector);

        mRotationMatrix1.setText(printFloatArray(rotationMatrix1));
        mRotationMatrix2.setText(printFloatArray(rotationMatrix2));
    }

    private String printFloatArray(float[] array) {
        String toPrint = "";

        for (int i = 0; i < array.length; i++) {
            toPrint += array[i];

            if (i != array.length - 1) {
                toPrint += ", ";
            }
        }

        return toPrint;
    }

    private float[] getGravity(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        String string = "x = " + x + ", y = " + y + ", z = " + z;
        mGravityView.setText(string);

        return new float[] {x, y, z};
    }

    private float[] getGeomagnetic(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        String string = "x = " + x + ", y = " + y + ", z = " + z;
        mGeomagneticView.setText(string);

        return new float[] {x, y, z};
    }

    private float[] getRotationVector(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        String string = "x = " + x + ", y = " + y + ", z = " + z;
        mRotationVectorView.setText(string);

        return new float[] {x, y, z};
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
