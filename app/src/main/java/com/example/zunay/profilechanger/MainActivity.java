package com.example.zunay.profilechanger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;

public abstract class MainActivity extends Activity implements SensorEventListener {

    TextView textView;
    SensorManager sensorManager;
    Sensor sensor;
    private AudioManager myAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        textView=findViewById(R.id.text);
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textView.setText(String.valueOf(sensorEvent.values[0]));
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        
    }
}
