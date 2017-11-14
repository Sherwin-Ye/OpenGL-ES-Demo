package com.sherwin.rapid.base.plugs.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherwin.Ye on 2017/2/15.9:40
 */
final public class Utils {

    private Utils() {
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (ActivityCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }
    /**
     * 查询未授权且会自动显示的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedNotShouldRationalePermissions(Activity activity, String... permission) {
        return findIfShouldRationalePermissions(activity, findDeniedPermissions(activity, permission), false);
    }

    /**
     * 查询未授权且不再显示的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedShouldRationalePermissions(Activity activity, String... permission) {
        return findIfShouldRationalePermissions(activity, findDeniedPermissions(activity, permission), true);
    }

    /**
     * 查询未授权且不再显示的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findIfShouldRationalePermissions(Activity activity, List<String> permission, boolean isShoouldRation) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, value)) {
                if (isShoouldRation) {
                    denyPermissions.add(value);
                }
            } else {
                if (!isShoouldRation) {
                    denyPermissions.add(value);
                }
            }
        }
        return denyPermissions;
    }


    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }
}

