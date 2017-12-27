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
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

    TextView textView;
    SensorManager sensorManager;
    Sensor sensor;
    static boolean flipOver;
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
        if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
            if(sensorEvent.values[0]<5){
                flipOver=true;
            }else {
                flipOver=false;
            }
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        try {
                            if (flipOver==true) {
                                if (myAudioManager != null) {
                                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                } else {
                                    Toast.makeText(getApplicationContext(), "AudioManager is null", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (myAudioManager != null) {
                                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
        Thread thread= new Thread(runnable);
        thread.start();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}