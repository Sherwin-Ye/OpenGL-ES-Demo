package com.sherwin.rapid.base.util.Terminal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;


import com.sherwin.rapid.base.core.RapidDroid;
import com.sherwin.rapid.base.util.LogUtil;
import com.sherwin.rapid.base.util.SPTool;
import com.sherwin.rapid.base.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author Sherwin.Ye
 * @data 2015-11-27 上午8:59:41
 * @desc TerminalUtil.java
 */
public class TerminalUtil {

    private static final String TAG = TerminalUtil.class.getSimpleName();
    public static final String KEY_ISROOT = "is_root";
    public static final String KEY_WIFIMAC = "wifi_mac";

    private static TerminalInfo mTerminalInfo;

    private static void initInfo(Context context) {
        mTerminalInfo = new TerminalInfo();
        mTerminalInfo.setHsman(Build.MANUFACTURER);
        mTerminalInfo.setHstype(Build.MODEL);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mTerminalInfo.setScreenHeight((short) dm.heightPixels);
        mTerminalInfo.setScreenWidth((short) dm.widthPixels);
        mTerminalInfo.setScreenDPI((short) dm.densityDpi);
        mTerminalInfo.setDensity(dm.density);


        mTerminalInfo.setSizeRAM(getRAMInfo());
        mTerminalInfo.setSizeROM(getROMInfo());
        mTerminalInfo.setSizeSD(getSDInfo());
        mTerminalInfo.setCpu(getCPUInfo());
        mTerminalInfo.setVersionOS(Build.VERSION.RELEASE);
        mTerminalInfo.setRoot(getRootSymbolRecord(context));
        mTerminalInfo.setLanguage(getLanguageInfo());
        mTerminalInfo.setHardware(Build.HARDWARE);
        mTerminalInfo.setBuildTime(Build.TIME);
        mTerminalInfo.setMac(getMacInfoRecord(context));
        mTerminalInfo.setImei(getImeiInfo());
        mTerminalInfo.setPhoneUuid(getPhoneUuid());
        mTerminalInfo.setImsi(getImsiInfo());
        mTerminalInfo.setPhoneType((short) getPhoneType(context));

        TerminalInfo cellInfo = getCellInfo(context);
        mTerminalInfo.setCellId(cellInfo.getCellId());
        mTerminalInfo.setLac(cellInfo.getLac());

        TerminalInfo locationInfo = getLocationInfo(context);
        mTerminalInfo.setLongitude(locationInfo.getLongitude());
        mTerminalInfo.setLatitude(locationInfo.getLatitude());
    }


    public static TerminalInfo getInfo() {
        if (null == mTerminalInfo) {
            initInfo(RapidDroid.application);
        }

        // 网络状态经常变化，实时获取
        NetworkInfo networkInfo = getNetworkInfo(RapidDroid.application);
        if (null != networkInfo) {
            mTerminalInfo.setNetworkType((short) networkInfo.getType());
            mTerminalInfo.setNetworkTypeName(networkInfo.getTypeName());
            mTerminalInfo.setNetworkTypeSub((short) networkInfo.getSubtype());
            mTerminalInfo.setNetworkTypeSubName(networkInfo.getSubtypeName());
        }

        return mTerminalInfo;
    }


    private static int getRAMInfo() {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            br = new BufferedReader(fr);
            String line = br.readLine();
            String[] lineArray = line.split("\\s+");
            int sizeRam = Integer.valueOf(lineArray[1]).intValue() / 1024;
            return sizeRam;
        } catch (IOException e) {
            LogUtil.e(TAG, "getRAMInfo IOException " + e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, "getRAMInfo IOException " + e);
                }
            }
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getROMInfo() {
        File romDir = Environment.getDataDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return (int) (romDir.getTotalSpace() / 1024 / 1024);
        } else {
            if (romDir.exists()) {
                StatFs stat = new StatFs(romDir.getPath());
                long blockSize = 0;
                long blockCount = 0;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                    blockSize = stat.getBlockSize();
                    blockCount = stat.getBlockCount();
                } else {
                    blockSize = stat.getBlockSizeLong();
                    blockCount = stat.getBlockCountLong();
                }
                return (int) (blockSize * blockCount / 1024 / 1024);
            } else {
                return 0;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getSDInfo() {
        File sdDir = Environment.getDataDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return (int) (sdDir.getTotalSpace() / 1024 / 1024);
        } else {
            if (sdDir.exists()) {
                StatFs stat = new StatFs(sdDir.getPath());
                long blockSize = 0;
                long blockCount = 0;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                    blockSize = stat.getBlockSize();
                    blockCount = stat.getBlockCount();
                } else {
                    blockSize = stat.getBlockSizeLong();
                    blockCount = stat.getBlockCountLong();
                }
                return (int) (blockSize * blockCount / 1024 / 1024);
            } else {
                return 0;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static long getSDCardSpace() {
        File sdDir = Environment.getDataDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return sdDir.getUsableSpace();
        } else {
            if (sdDir.exists()) {
                StatFs stat = new StatFs(sdDir.getPath());
                return stat.getAvailableBlocks();
            } else {
                return 0;
            }
        }
    }

    private static String getCPUInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String str3 = "";
        String ret = "";
        String[] strArray;
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(str1);
            br = new BufferedReader(fr, 8192);
            str2 = br.readLine();
            strArray = str2.split("\\s+");
            for (int i = 2; i < strArray.length; i++) {
                ret = ret + strArray[i] + " ";
            }

            str3 = br.readLine();
            if (str3.contains("model name")) {
                strArray = str3.split("\\s+");
                for (int i = 2; i < strArray.length; i++) {
                    ret += strArray[i] + " ";
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getCPUInfo Exception " + e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, "getCPUInfo Exception " + e);
                }
            }
        }
        return ret;
    }

    private static Boolean getRootSymbolRecord(Context context) {

        Boolean isRoot = SPTool.getParamToBoolean(context,KEY_ISROOT,false);
        if (null == isRoot) {
            isRoot = getRootSymbol();
            SPTool.setParam(context,KEY_ISROOT,isRoot);
        }
        return isRoot;
    }

    private static boolean getRootSymbol() {
        boolean isRoot = false;
        String sys = System.getenv("PATH");
        ArrayList<String> commands = new ArrayList<String>();
        String[] path = sys.split(":");
        for (int i = 0; i < path.length; i++) {
            String commod = "ls -l " + path[i] + "/su";
            commands.add(commod);
        }
        ArrayList<String> res = new ArrayList<String>();
        Process process = null;
        BufferedOutputStream shellInput = null;
        BufferedReader shellOutput = null;
        try {
            process = Runtime.getRuntime().exec("/system/bin/sh");
            shellInput = new BufferedOutputStream(process.getOutputStream());
            shellOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            for (String command : commands) {
                shellInput.write((command + " 2>&1\n").getBytes());
            }

            shellInput.write("exit\n".getBytes());
            shellInput.flush();

            String line;
            while ((line = shellOutput.readLine()) != null) {
                res.add(line);
            }

            process.waitFor();
        } catch (Exception e) {
            LogUtil.e(TAG, "getRootSymbol error : " + e);
        } finally {
            try {
                if (null != process) {
                    process.destroy();
                }
                if (null != shellInput) {
                    shellInput.close();
                }
                if (null != shellOutput) {
                    shellOutput.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        String response = "";
        for (int i = 0; i < res.size(); i++) {
            response += res.get(i);
        }
        String root = "-rwsr-sr-x root root";
        if (response.replaceAll(" ", "").contains(root.replaceAll(" ", ""))) {
            isRoot = true;
        }
        return isRoot;
    }

    private static String getLanguageInfo() {
        String language = Locale.getDefault().getLanguage();// 获取系统当前使用的语言
        String country = Locale.getDefault().getCountry();// 获取区域
        return language + "-" + country;
    }

    private static String getMacInfoRecord(Context context) {
        String mac = SPTool.getParamToString(context,KEY_WIFIMAC,"");
        if (StringUtil.isBlank(mac)) {
            mac = getMacInfo(context);
            SPTool.setParam(context,KEY_WIFIMAC,mac);
        }
        return mac;
    }

    private static String getMacInfo(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    mac = info.getMacAddress();
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getMacInfo error : " + e);
        }
        return mac;
    }

    public static String getImeiInfo() {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) RapidDroid.application.getSystemService(TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            LogUtil.e(TAG, "getImeiInfo error : " + e);
        }
        return imei;
    }

    // 得到imei的替代策略
    public static String getPhoneUuid() {
        String phoneUuid = SPTool.getParamToString(RapidDroid.application,"ANTS_IMEI_REPLACE_PHONE_UUID",null);
        if (StringUtil.isBlank(phoneUuid)) {
            phoneUuid = StringUtil.getUUID();
            SPTool.setParam(RapidDroid.application,"ANTS_IMEI_REPLACE_PHONE_UUID", phoneUuid);
        }
        return phoneUuid;
    }


    public static String getImsiInfo() {
        String imsi = "";
        try {
            imsi = ((TelephonyManager) RapidDroid.application.getSystemService(TELEPHONY_SERVICE))
                    .getSubscriberId();
        } catch (Exception e) {
            LogUtil.e(TAG, "getImeiInfo error : " + e);
        }
        return imsi;
    }

    private static int getPhoneType(Context context) {
        int type = -1;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            type = tm.getPhoneType();
        } catch (Exception e) {
            LogUtil.e(TAG, "getPhoneType error : " + e);
        }
        return type;
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            LogUtil.e(TAG, "getNetworkType error : " + e);
        }
        return networkInfo;
    }

    private static TerminalInfo getCellInfo(Context context) {
        TerminalInfo cellInfo = new TerminalInfo();
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            CellLocation location = tm.getCellLocation();
            if (location instanceof GsmCellLocation) {
                cellInfo.setCellId(((GsmCellLocation) location).getCid());
                cellInfo.setLac(((GsmCellLocation) location).getLac());
            } else if (location instanceof CdmaCellLocation) {
                cellInfo.setCellId(((CdmaCellLocation) location).getBaseStationId());
                cellInfo.setLac(((CdmaCellLocation) location).getNetworkId());
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getCellInfo error : " + e);
        }
        return cellInfo;
    }

    private static TerminalInfo getLocationInfo(Context context) {
        TerminalInfo locationInfo = new TerminalInfo();
        try {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (null != lm) {
                Location location = null;
                try {
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "getLocationInfo by gps error : " + e);
                }
                if (null == location) {
                    if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        LocationListener locationListener = new LocationListener() {
                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            @Override
                            public void onProviderEnabled(String provider) {
                            }

                            @Override
                            public void onProviderDisabled(String provider) {
                            }

                            @Override
                            public void onLocationChanged(Location location) {
                            }
                        };
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                        location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (null != location) {
                    locationInfo.setLongitude(String.valueOf(location.getLongitude()));
                    locationInfo.setLatitude(String.valueOf(location.getLatitude()));
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getLocationInfo error : " + e);
        }
        return locationInfo;
    }

    public synchronized static boolean isRootAhth() {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtil.e("*** DEBUG ***", "Unexpected error - Here is what I know: "
                    + e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
