package com.sherwin.rapid.base.plugs.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.sherwin.radroid.base.R;
import com.sherwin.rapid.base.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherwin.Ye on 2017/2/15.9:22
 */
public class PermissionGen {
    private static final String TAG = PermissionGen.class.getSimpleName();

    private String[] mPermissions;
    private int mRequestCode;
    private Object object;

    private PermissionGen(Object object) {
        this.object = object;
    }

    public static PermissionGen with(Activity activity) {
        return new PermissionGen(activity);
    }

    public static PermissionGen with(Fragment fragment) {
        return new PermissionGen(fragment);
    }

    public PermissionGen permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionGen addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request() {
        requestPermissions(object, mRequestCode, mPermissions);
    }

    public static void needPermission(Activity activity, int requestCode, String... permissions) {
        requestPermissions(activity, requestCode, permissions);
    }

    public static void needPermission(Fragment fragment, int requestCode, String... permissions) {
        requestPermissions(fragment, requestCode, permissions);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private static void requestPermissions(final Object object, final int requestCode, String[] permissions) {
        if (object == null) {
            return;
        }
        final Activity activity = Utils.getActivity(object);

        LogUtil.i(TAG, "requestPermission requestCode:" + requestCode);
        List<String> permissionsList = Utils.findDeniedNotShouldRationalePermissions(activity, permissions);
        final List<String> shouldRationalePermissionsList = Utils.findDeniedShouldRationalePermissions(activity, permissions);


        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);
//        if (Build.VERSION.SDK_INT < 23) {
//            permissionGrant.onPermissionGranted(requestCode);
//            return;
//        }

        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), requestCode);
            LogUtil.d(TAG, "showMessageOKCancel requestPermissions");

        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel(activity, activity.getResources().getString(R.string.permission_tip),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                                    requestCode);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IPermissionGrant iPermissionGrant = null;
                            if (object instanceof IPermissionGrant) {
                                iPermissionGrant = (IPermissionGrant) object;
                            }
                            iPermissionGrant.onPermissionGranted(requestCode,false,null);
                        }
                    });
        } else {
            IPermissionGrant iPermissionGrant = null;
            if (object instanceof IPermissionGrant) {
                iPermissionGrant = (IPermissionGrant) object;
            }
            iPermissionGrant.onPermissionGranted(requestCode,true,null);
        }
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.common_ok, okListener)
                .setNegativeButton(R.string.common_refuse, cancelListener)
                .create()
                .show();
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        IPermissionGrant iPermissionGrant = null;
        if (obj instanceof IPermissionGrant) {
            iPermissionGrant = (IPermissionGrant) obj;
        }
        if (iPermissionGrant != null) {
            iPermissionGrant.onPermissionGranted(requestCode, deniedPermissions.size() <= 0, deniedPermissions);
        }
        LogUtil.i("权限开启" + (deniedPermissions.size() <= 0 ? "成功" : "失败"));
    }
}
