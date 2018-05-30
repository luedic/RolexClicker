package net.ictcampus.luedic.rolexclicker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

class ShakeEventListener implements SensorEventListener{

    private static final int MIN_FORCE = 10;

    private static final int MIN_DIRECTION_CHANGE = 2;

    private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 500;

    private static final int MAX_TOTAL_DURATION_OF_SHAKE = 1000;

    private long mFirstDirectionChangeTime = 0;

    private long mLastDirectionChangeTime;

    private int mDirectionChangeCount = 0;

    /** The last x position. */
    private float lastX = 0;

    /** The last y position. */
    private float lastY = 0;

    /** The last z position. */
    private float lastZ = 0;

    private OnShakeListener newShakeListener;

    public interface OnShakeListener {

        void onShake();
    }

    public void setOnShakeListener(OnShakeListener listener){

        newShakeListener = listener;

    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        //Log.d("XVal", String.valueOf(event.values[0]));

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

        if (totalMovement > MIN_FORCE) {
            //Log.d("Minforce", String.valueOf(totalMovement));
            long now = System.currentTimeMillis();

            if (mFirstDirectionChangeTime == 0) {
                //Log.d("firstset", String.valueOf(now));

                mFirstDirectionChangeTime = now;
                mLastDirectionChangeTime = now;
            }

            long lastChangeWasAgo = now - mLastDirectionChangeTime;
            //----------------------
            if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

                // store movement data
                mLastDirectionChangeTime = now;
                mDirectionChangeCount++;

                // store last sensor data
                lastX = x;
                lastY = y;
                lastZ = z;
                //-------------------------
                Log.d("Direction change", String.valueOf(mDirectionChangeCount));
                //Log.d("duration", String.valueOf(lastChangeWasAgo));
                if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {
                    Log.d("duration", String.valueOf(lastChangeWasAgo));
                    long totalDuration = now - mFirstDirectionChangeTime;

                    if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                        Log.d("On shake", String.valueOf(totalMovement));
                        newShakeListener.onShake();
                        resetShakeParameters();
                    }
                }
            }
            else {

                resetShakeParameters();

            }

        }
    }
        private void resetShakeParameters () {
            mFirstDirectionChangeTime = 0;
            mDirectionChangeCount = 0;
            mLastDirectionChangeTime = 0;
            lastX = 0;
            lastY = 0;
            lastZ = 0;
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
