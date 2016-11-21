package com.example.pedometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor sensor;
    int dir_UP =0;
    int dir_DOWN =0;
    int count =0;
    double acceleration=0;
    double gravity = 9.81;
    TextView walk,step;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        walk = (TextView)findViewById(R.id.textView2);
        step = (TextView)findViewById(R.id.textView3);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    protected void onResume() {
        super.onResume();

        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);//리스너 등록
    }

    @Override
    protected void onPause() {
        super.onPause();

        sm.unregisterListener(this);//리스너 해제
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            acceleration = Math.sqrt(x*x+y*y+z*z);
        }
        if(acceleration - gravity >5) {
            dir_UP =1;
        }
        if(dir_UP == 1 && gravity - acceleration > 5)
        {
            dir_DOWN =1;
        }

        if(dir_DOWN == 1)
        {
            count++;
            step.setText("" + count + " Steps!!");

            dir_UP =0;
            dir_DOWN =0;
        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
