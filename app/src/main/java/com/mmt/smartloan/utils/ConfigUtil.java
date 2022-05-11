package com.mmt.smartloan.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.icu.util.TimeZone;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.mmt.smartloan.BuildConfig;
import com.mmt.smartloan.MyApplication;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class ConfigUtil {

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static String getAndroidID(Context context) {
        String androidId = "";
        try {
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
        return androidId;
    }


    public static int getAdrVersion(Context context) {
        int result = 0;
        try {
            result = Build.VERSION.SDK_INT;
        } catch (Exception e) {
            return 0;
        }
        return result;
    }

    public static String getAppVersion(Context context) {
        String result = "";
        try {
            result = BuildConfig.VERSION_NAME;
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static String getIMEI(Context context) {
        String result = "00000000000000";
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "00000000000000";
            }
            result = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            return "00000000000000";
        }
        return result;
    }

    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static boolean isRoot() {
        boolean isRoot = false;
        try {
            File su = new File("/system/bin/su");
            File su2 = new File("/system/bin/su");
            if (su.exists() && su2.exists()) {
                isRoot = true;
            } else {
                isRoot = false;
            }
        } catch (Exception e) {

        }
        return isRoot;
    }


    public static String getLanguageCode(Context context) {
        String result = "";
        Locale locale = Locale.getDefault();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                locale = context.getResources().getConfiguration().getLocales().get(0);
                result = locale.getLanguage();
            } catch (Exception e) {

            }
        }
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentTimeZoneId() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID() + "";
    }

    public static String createGmtOffsetString(boolean includeGmt, boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;

        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');

        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();

    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);

        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 获取SIM卡运营商
     *
     * @param context
     * @return
     */
    public static String getOperators(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String IMSI = tm.getSubscriberId();
        if (IMSI == null || IMSI.equals("")) {
            return operator;
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            operator = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            operator = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            operator = "中国电信";
        }
        return operator;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    public static String getCountryCode(Context context){
        String result = "";
        try {
            TelephonyManager tm =
                    (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getSimCountryIso();
        }catch (Exception e){

        }
        return result;
    }


}
