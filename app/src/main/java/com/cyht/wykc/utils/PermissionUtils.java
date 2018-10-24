package com.cyht.wykc.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.view.setting.SettingFragment;

/**
 * Created by qianxiaoai on 2016/7/7.
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();
    public static final int CODE_ACCESS_FINE_LOCATION = 0;
    public static final int CODE_ACCESS_COARSE_LOCATION = 1;
    public static final int CODE_READ_EXTERNAL_STORAGE = 2;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 3;
    public static final int CODE_CALL_PHONE = 4;
    public static final int CODE_GET_ACCOUNTS = 5;
    public static final int CODE_CAMERA = 6;
    public static final int CODE_MULTI_ESSENTIAL_PERMISSION = 100;
    public static final int CODE_MULTI_ANOTHER_PERMISSION = 101;//非essential的权限


    //缺失就重启app，提示客户授权的重要权限
    private static final String[] essentialPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    //全部权限
    private static final String[] requestAllPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.CAMERA
//            Manifest.permission.WRITE_SETTINGS,
    };

    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }


    /**
     * 判断app运行必须拥有的权限是否已经授权
     *
     * @param activity
     * @param requestCodes
     * @return
     */
    public static boolean isPermissionsGranted(final Activity activity, int[] requestCodes) {
        if (activity == null) {
            return true;
        }

        if (requestCodes.length > 0) {
            boolean isAllGranted = true;
            for (int i : requestCodes) {
                try {
                    isAllGranted = isAllGranted && (ActivityCompat.checkSelfPermission(activity, requestAllPermissions[i]) == PackageManager.PERMISSION_GRANTED);
                } catch (RuntimeException e) {
                    Toast.makeText(activity, "please open this permission", Toast.LENGTH_SHORT)
                            .show();
                    Log.e(TAG, "RuntimeException:" + e.getMessage());
                    return false;
                }
            }
            return isAllGranted;
        }
        return true;
    }

    /**
     * Requests permission.
     * 申请单个权限
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestPermission(final Activity activity, final int requestCode, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }

        Log.i(TAG, "requestPermission requestCode:" + requestCode);
        if (requestCode < 0 || requestCode >= requestAllPermissions.length) {
            Log.w(TAG, "requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestAllPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以在这个地方，低于23就什么都不做，
        // 个人建议try{}catch(){}单独处理，提示用户开启权限。
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            if (Build.VERSION.SDK_INT < 23) {
                //dialog提示客户选择是否跳转到本app权限窗口，不确定就关闭app，确定就跳转


            }
            Toast.makeText(activity, "please open this permission", Toast.LENGTH_SHORT)
                    .show();
            Log.e(TAG, "RuntimeException:" + e.getMessage());
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED");


            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                Log.i(TAG, "requestPermission shouldShowRequestPermissionRationale");
//                shouldShowRationale(activity, requestCode, requestPermission);
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);

            } else {
                Log.d(TAG, "requestCameraPermission else");
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }

        } else {
            Log.d(TAG, "ActivityCompat.checkSelfPermission ==== PackageManager.PERMISSION_GRANTED");
            Toast.makeText(activity, "opened:" + requestAllPermissions[requestCode], Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(requestCode);
        }
    }


    /**
     * Requests permission.  在fragment中
     * 申请单个权限
     *
     * @param fragment
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestPermission(final SettingFragment fragment, final int requestCode, PermissionGrant permissionGrant) {
        if (fragment == null) {
            return;
        }

        Log.i(TAG, "requestPermission requestCode:" + requestCode);
        if (requestCode < 0 || requestCode >= requestAllPermissions.length) {
            Log.w(TAG, "requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestAllPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以在这个地方，低于23就什么都不做，
        // 个人建议try{}catch(){}单独处理，提示用户开启权限。
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(fragment.getActivity(), requestPermission);
        } catch (RuntimeException e) {
            if (Build.VERSION.SDK_INT < 23) {
                //dialog提示客户选择是否跳转到本app权限窗口，不确定就关闭app，确定就跳转


            }
//            Toast.makeText(fragment.getActivity(), "please open this permission", Toast.LENGTH_SHORT)
//                    .show();
            Log.e(TAG, "RuntimeException:" + e.getMessage());
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED");


            if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), requestPermission)) {
                Log.i(TAG, "requestPermission shouldShowRequestPermissionRationale");
//                shouldShowRationale(activity, requestCode, requestPermission);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragment.requestPermissions(new String[]{requestPermission}, requestCode);
                }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fragment.requestPermissions(new String[]{requestPermission}, requestCode);
                }
            }

        } else {
            Log.d(TAG, "ActivityCompat.checkSelfPermission ==== PackageManager.PERMISSION_GRANTED");
//            Toast.makeText(fragment.getActivity(), "opened:" + requestAllPermissions[requestCode], Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(requestCode);
        }
    }


    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, int requestCode, PermissionGrant permissionGrant) {

        if (activity == null) {
            return;
        }

        //TODO
        Log.d(TAG, "onRequestPermissionsResult permissions length:" + permissions.length);
        Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
//            Toast.makeText(activity, "all permission success" + notGranted, Toast.LENGTH_SHORT)
//                    .show();
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            Set<String> stringSet = new HashSet<>();
            String message = "";
            for (String permission : notGranted) {
                if (permission.equals(requestAllPermissions[PermissionUtils.CODE_ACCESS_FINE_LOCATION]) || permission.equals(requestAllPermissions[PermissionUtils.CODE_ACCESS_COARSE_LOCATION])) {
                    stringSet.add("您的位置");
                } else if (permission.equals(requestAllPermissions[PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE]) || permission.equals(requestAllPermissions[PermissionUtils.CODE_READ_EXTERNAL_STORAGE])) {
                    stringSet.add("存储");
                } else if (permission.equals(requestAllPermissions[PermissionUtils.CODE_CALL_PHONE])) {
                    stringSet.add("电话");
                } else if (permission.equals(requestAllPermissions[PermissionUtils.CODE_GET_ACCOUNTS])) {
                    stringSet.add("通讯录");
                } else if (permission.equals(requestAllPermissions[PermissionUtils.CODE_CAMERA])) {
                    stringSet.add("相机");
                }
            }
            int a = 0;
            for (String s : stringSet) {
                if (a == 0) {
                    message = s;
                } else {
                    message = message + "、" + s;
                }
                a++;
            }
            openSettingActivity(activity, "我要看车需要" + message + "权限，是否去设置", requestCode);
        }
    }

    /**
     * 申请指定的多个非essential权限
     */
    public static void requestMultiAnotherPermissions(final Activity activity, int[] requestCodes, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        String[] permissionArray = new String[requestCodes.length];
        for (int i = 0; i < requestCodes.length; i++) {
            permissionArray[i] = requestAllPermissions[requestCodes[i]];
        }
        if (requestCodes.length > 0) {
            ActivityCompat.requestPermissions(activity, permissionArray,
                    CODE_MULTI_ANOTHER_PERMISSION);
        }
    }

    /**
     * 一次申请多个权限，申请ESSENTIALPERMISSION(必需)中所有权限
     */
    public static void requestMultiEssentialPermissions(final Activity activity, PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedEssentialPermission(activity, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedEssentialPermission(activity, true);

        //TODO checkSelfPermission
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_ESSENTIAL_PERMISSION);
            Log.d(TAG, "showMessageOKCancel requestPermissions");

        } else if (shouldRationalePermissionsList.size() > 0) {
//            showMessageOKCancel(activity, "should open those permission",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
//                                    CODE_MULTI_ESSENTIAL_PERMISSION);
//                            Log.d(TAG, "showMessageOKCancel requestPermissions");
//                        }
//                    });

            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                    CODE_MULTI_ESSENTIAL_PERMISSION);
            Log.d(TAG, "showMessageOKCancel requestPermissions");

        } else {
            grant.onPermissionGranted(CODE_MULTI_ESSENTIAL_PERMISSION);
        }

    }


    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(activity, "Rationale: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
                Log.d(TAG, "showMessageOKCancel requestPermissions:" + requestPermission);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", cancleListener)
                .create()
                .show();

    }

    /**
     * 申请必需权限的结果分析
     *
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                @NonNull int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return;
        }
        Log.d(TAG, "requestPermissionsResult requestCode:" + requestCode);

        if (requestCode == CODE_MULTI_ESSENTIAL_PERMISSION || requestCode == CODE_MULTI_ANOTHER_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, requestCode, permissionGrant);
            return;
        }


        if (requestCode < 0 || requestCode >= requestAllPermissions.length) {
            Log.w(TAG, "requestPermissionsResult illegal requestCode:" + requestCode);
//            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(TAG, "onRequestPermissionsResult requestCode:" + requestCode + ",permissions:" + permissions.toString()
                + ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onRequestPermissionsResult PERMISSION_GRANTED");
            //TODO success, do something, can use callback
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            //TODO hint user this permission function
            Log.i(TAG, "onRequestPermissionsResult PERMISSION NOT GRANTED");
            //TODO
            if (requestCode == CODE_READ_EXTERNAL_STORAGE || requestCode == CODE_WRITE_EXTERNAL_STORAGE) {
                Toast.makeText(activity, "没有此权限，无法存储", Toast.LENGTH_SHORT).show();
            } else if (requestCode == CODE_CALL_PHONE) {
                Toast.makeText(activity, "没有此权限，无法打电话", Toast.LENGTH_SHORT).show();
            } else if (requestCode == CODE_GET_ACCOUNTS) {
                Toast.makeText(activity, "没有此权限，无法使用通讯录", Toast.LENGTH_SHORT).show();
            } else if (requestCode == CODE_CAMERA) {
                Toast.makeText(activity, "没有此权限，无法使用相机", Toast.LENGTH_SHORT).show();
            }
//            String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
//            openSettingActivity(activity, "Result" + permissionsHint[requestCode],requestCode);
        }
    }


    private static void openSettingActivity(final Activity activity, String message, final int requestCode) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//确定按钮
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Log.d(TAG, "getPackageName(): " + activity.getPackageName());
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
                if (requestCode==CODE_MULTI_ESSENTIAL_PERMISSION) {
                    ActivityManagerUtils.getInstance().appExit();//退出程序
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//取消按钮
                if (requestCode == CODE_MULTI_ESSENTIAL_PERMISSION) {
                    ActivityManagerUtils.getInstance().appExit();//退出程序
                }else if (requestCode==CODE_MULTI_ANOTHER_PERMISSION){
                    Toast.makeText(activity, "没有权限，无法使用相应功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return requestPermissions所列权限中，没有得到授权的需要说明与不需要说明的权限
     */
    public static ArrayList<String> getNoGrantedEssentialPermission(Activity activity, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < essentialPermissions.length; i++) {
            String requestPermission = essentialPermissions[i];


            //TODO checkSelfPermission
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
//                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
//                        .show();
                Log.e(TAG, "RuntimeException:" + e.getMessage());
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }

}

