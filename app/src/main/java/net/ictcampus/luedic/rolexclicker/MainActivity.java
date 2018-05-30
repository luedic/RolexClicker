package net.ictcampus.luedic.rolexclicker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager newSensorManager;
    private ShakeEventListener newSensorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--------------Sensor-----------------
        newSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        newSensorListener = new ShakeEventListener();

        newSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {

                Toast.makeText(MainActivity.this, "Shake!", Toast.LENGTH_SHORT).show();

            }
        });


        //---------------------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            TextView tv = (TextView) findViewById(R.id.textView);
            long score = 0;
            @Override
            public void onClick(View v) {
                score+=1;
                tv.setText("Score: "+score);
            }
        });
        Button btnShop = (Button) findViewById(R.id.buttonshop);
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        newSensorManager.registerListener(newSensorListener,
                newSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        newSensorManager.unregisterListener(newSensorListener);
        super.onPause();
    }
}

