package net.ictcampus.luedic.rolexclicker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class ShakeEventListener implements SensorEventListener{

    private static final int MIN_FORCE = 10;

    private static final int MIN_DIRECTION_CHANGE = 1;

    private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;

    private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;

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

        float x = event.values[SensorManager.DATA_X];
        float y = event.values[SensorManager.DATA_Y];
        float z = event.values[SensorManager.DATA_Z];

        float totalMovement = Math.abs(x+y+z-lastX-lastY-lastZ);

        if (totalMovement > MIN_FORCE){

            long now  = System.currentTimeMillis();

            if (mFirstDirectionChangeTime == 0) {
                mFirstDirectionChangeTime = now;
                mLastDirectionChangeTime = now;
            }

            long lastChangeWasAgo = now - mLastDirectionChangeTime;

            if(lastChangeWasAgo >= MIN_DIRECTION_CHANGE){

                long totalDuration = now - mFirstDirectionChangeTime;
                if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                    newShakeListener.onShake();
                    resetShakeParameters();
                }
            }

            else{
                resetShakeParameters();
            }

        }

    }
    private void resetShakeParameters() {
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
