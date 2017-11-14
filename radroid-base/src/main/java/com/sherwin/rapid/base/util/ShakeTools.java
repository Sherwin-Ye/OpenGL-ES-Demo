package com.sherwin.rapid.base.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.16:01
 * @desc 摇一摇工具类
 */
public class ShakeTools {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private static final int UPTATE_INTERVAL_TIME = 50;
    private static final int SPEED_SHRESHOLD = 40;//这个值调节灵敏度
    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;

    private Context mContext;

    private boolean isShaking = false;

    private OnShakeCallback mOnShakeCallback;

    public void init(Context context) {
        mContext = context;
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (sensor != null) {
            sensorManager.registerListener(sensorEventListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_UI);//这里选择感应频率
        }
    }

    /**
     * 销毁
     */
    public void destory(){
        if (sensorManager!=null){
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {
            PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
            if (!pm.isScreenOn()) {// 屏幕熄灭时不允许摇一摇
                return;
            }
            long currentUpdateTime = System.currentTimeMillis();
            long timeInterval = currentUpdateTime - lastUpdateTime;
            if (timeInterval < UPTATE_INTERVAL_TIME) {
                return;
            }
            lastUpdateTime = currentUpdateTime;
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;

            lastX = x;
            lastY = y;
            lastZ = z;
            double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY
                    + deltaZ * deltaZ) / timeInterval) * 100;
            if (speed >= SPEED_SHRESHOLD) {
                if (!isShaking) {
                    vibrator.vibrate(300);
                    isShaking = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isShaking = false;
                        }
                    }, 2000);
                    if (mOnShakeCallback!=null){
                        mOnShakeCallback.onShake();
                    }
                }
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public OnShakeCallback getOnShakeCallback() {
        return mOnShakeCallback;
    }

    public void setOnShakeCallback(OnShakeCallback onShakeCallback) {
        mOnShakeCallback = onShakeCallback;
    }

    public static interface OnShakeCallback{
        void onShake();
    }
}
