package com.goldze.common.dmvvm.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GuoFeng
 * @date : 2019/1/19 21:15
 * @description: 6.0动态权限管理帮助类
 */
public class PermissionsUtils {

    public static boolean checkPermissions(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            List<String> permissionsList = new ArrayList<>();
            if (permissions != null && permissions.length != 0) {
                for (String permission : permissions) {
                    if (!isHavePermissions(activity, permission)) {
                        permissionsList.add(permission);
                    }
                }
                if (permissionsList.size() > 0) {
                    // 遍历完后申请
                    applyPermissions(activity, permissionsList);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查是否授权某权限
     */
    private static boolean isHavePermissions(Activity activity, String permissions) {
        return ContextCompat.checkSelfPermission(activity, permissions) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请权限
     */
    private static void applyPermissions(Activity activity, List<String> permissions) {
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), 1);
        }
    }
}
