package com.example.sensordeposicion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText posicion;
    private SensorManager sm;
    private Sensor sa;
    private SensorEventListener SEL;
    private int latigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {4
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posicion = findViewById(R.id.et_posicion);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sa = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sa == null){
            SEL = new SensorEventListener() {

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float x = sensorEvent.values[0];
                    posicion.setText(Float.toString(x));
                    if (x > -5 && latigo == 0){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    }else if (x > 5 && latigo == 1){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                    }
                    if(latigo == 2){
                        sonido();
                        latigo = 0;
                    }
                }

            };
        }
    }
    private void sonido(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.latigosheldon);
        mp.start();
    }
    public void iniciar(){
        sm.registerListener(SEL, sa, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onResume(){
        iniciar();
        super.onResume();
    }
    public void detener(){
        sm.unregisterListener(SEL);
    }
    protected void onStop(){
        detener();
        super.onStop();
    }
}