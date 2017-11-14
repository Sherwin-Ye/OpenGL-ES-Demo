package com.sherwin.rapid.base.util;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.16:36
 * @desc GPS 相关功能
 */
public class GPSUtil {
    /**
     * 计算两个坐标之间的距离
     * @param lat1 A经度
     * @param lon1 A纬度
     * @param lat2 B经度
     * @param lon2 B纬度
     * @return 米
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lon1 * Math.PI / 180 - lon2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
