package com.sherwin.rapid.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sherwin.rapid.base.core.RapidDroid;

public class NetUtil {
    //net state
    public static final int WIFI = 1;
    public static final int MOBILE = 2;
    public static final int DISCONNET = 3;

    private static final String TAG = "NetUtil";

    public static int getNetState(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobile.isConnected() && !wifi.isConnected()) {
                return DISCONNET;
            } else {
                if (!wifi.isConnected())
                    return MOBILE;
                else
                    return WIFI;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getNetState error : " + e);
            return DISCONNET;
        }
    }

    /**
     * 网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetWorking(Context context){
        return getNetState(context)!=DISCONNET;
    }
    /**
     * 网络是否可用
     * @return
     */
    public static boolean isNetWorking(){
        return getNetState(RapidDroid.application)!=DISCONNET;
    }

    /**
     * @hide
     * TODO: makes real freq boundaries
     */
    public static boolean is24GHz(int freq) {
        return freq > 2400 && freq < 2500;
    }

    /**
     * @hide
     * TODO: makes real freq boundaries
     */
    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }

}
