package com.sherwin.rapid.base.plugs.wifi;

import android.app.Application;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Sherwin.Ye on 2017/2/17.16:16
 * wifi 扫描工具
 */
public class WifiSDK {
    /**
     * 得到已经连接上的wifi信息
     *
     * @param application
     * @return
     */
    public static WifiInfo getConnectionWifiInfo(Application application) {
        WifiManager wifiManager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (TextUtils.isEmpty(wifiInfo.getSSID())) {
            return null;
        }
        return wifiInfo;
    }


    /**
     * 获得周围环境中wifi信号
     *
     * @param application
     * @return
     */
    public static List<ScanResult> getWifiScanResuls(Application application) {
        WifiManager wifiManager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> results = wifiManager.getScanResults();
        sortByLevel(results);
        removeDuplicates(results);
        return results;
    }

    /**
     * 根据信号强度排序
     *
     * @param list
     */
    private static void sortByLevel(List<ScanResult> list) {
        Collections.sort(list, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult sr1, ScanResult sr2) {
                return sr1.level - sr2.level;
            }
        });
    }

    /**
     * 去除重复部分
     *
     * @param list
     */
    private static void removeDuplicates(List<ScanResult> list) {
        Set<String> ssidSet = new HashSet<>();
        Iterator<ScanResult> iterator = list.iterator();
        while (iterator.hasNext()) {
            ScanResult scanResult = iterator.next();
            if (ssidSet.contains(scanResult.SSID)) {
                iterator.remove();
            } else {
                ssidSet.add(scanResult.SSID);
            }
        }
        ssidSet.clear();
    }
}
