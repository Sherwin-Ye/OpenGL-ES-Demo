package com.sherwin.rapid.base.plugs.permission;

import java.util.List;

/**
 * Created by Sherwin.Ye on 2017/2/15.13:15
 */
public interface IPermissionGrant {
    void onPermissionGranted(int requestCode, boolean isSuccess, List<String> permissions);
}
