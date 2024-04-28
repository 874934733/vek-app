package com.yingyangfly.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author MaTianyu
 * @date 2014-12-10
 */
public class AppUtil {

    /**
     * 是否是主进程
     */
    public static boolean isMainProcess() {
        return ProcessUtils.isMainProcess();
    }

    /**
     * 获取当前进程名字
     */
    public static String getCurrentProcessName() {
        return ProcessUtils.getCurrentProcessName();
    }

    /**
     * need < uses-permission android:name =“android.permission.GET_TASKS” />
     * 判断是否前台运行
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            return componentName != null && componentName.getPackageName().equals(context.getPackageName());
        }
        return false;
    }

    /**
     * 获取App包 信息版本号
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName() {
        return AppUtils.getAppVersionName();
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode() {
        return AppUtils.getAppVersionCode();
    }

    public static void killTheApp() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    public static Intent callPhone(String phone) {
        //获取输入的电话号码
        //创建打电话的意图
        Intent intent = new Intent();
        //设置拨打电话的动作
        intent.setAction(Intent.ACTION_DIAL);
        //设置拨打电话的号码
        intent.setData(Uri.parse("tel:" + phone));
        //开启打电话的意图
        return intent;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(String phoneNum) {
        PhoneUtils.dial(phoneNum);
    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber
     * @param message
     */
    public static void sendSms(String phoneNumber, String message) {
        PhoneUtils.sendSms(phoneNumber, message);
    }

    /**
     * 比较VersionName
     *
     * @param oldVersion 当前安装版本。
     * @param newVersion 服务器最新版本。
     * @return
     */
    public static boolean compareVersionName(String oldVersion, String newVersion) {
        String[] oldV = oldVersion.split("\\.");
        String[] newV = newVersion.split("\\.");
        oldVersion = oldVersion.replace(".", "");
        newVersion = newVersion.replace(".", "");
        int oVersion = Integer.parseInt(oldVersion);
        int nVersion = Integer.parseInt(newVersion);
        return oVersion < nVersion;
    }

    /**
     * Convert a translucent themed Activity
     * 将Activity 改为透明
     */
    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms before Android 5.0
     */
    public static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[]{
                    null
            });
        } catch (Throwable t) {
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms after Android 5.0
     */
    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable t) {
        }
    }

    public static String getAndroidId() {
        return DeviceUtils.getAndroidID();
    }

    public static Context getContext() {
        return Utils.getApp();
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
} 