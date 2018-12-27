package ss.com.toolkit.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获取设备信息工具类
 */
public class DeviceUtil {
    private static final String TAG = DeviceUtil.class.getSimpleName();

    /**
     * 获取设备的系统版本号
     */
    public static int getSDKVersion() {
        int sdk = Build.VERSION.SDK_INT;
        return sdk;
    }

    /**
     * Gets os version.
     *
     * @return the os version
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Gets build id.
     *
     * @return the build id
     */
    public static String getBuildID() {
        return Build.ID;
    }

    /**
     * 获取设备的型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        return model;
    }

    /**
     * 获取设备的型号
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * Gets serial.
     *
     * @return the serial
     */
    public static String getSerial() {
        String serial = Build.SERIAL;
        return serial;
    }

    /**
     * Gets device.
     *
     * @return the device
     */
    public static String getDevice() {
        String device = Build.DEVICE;
        return device;
    }

    /**
     * Gets getManufacturer.
     *
     * @return the Manufacturer
     */
    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        return manufacturer;
    }


    /**
     * Gets DeviceBrand
     *
     * @return the BRAND
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机设备信息
     *
     * @return
     */
    public static String getDeviceInfo() {
        if (TextUtils.isEmpty(getModel()) || TextUtils.isEmpty(getDeviceBrand())) {
            return "";
        }

        // 有些设备android.os.Build.MODEL不会带上制造商名称(比如小米)，避免重复，这里需要进行判断。
        if (getModel().toLowerCase().contains(getDeviceBrand().toLowerCase())) {
            return getModel();
        }

        return getDeviceBrand() + " " + getModel();
    }

    /**
     * Gets string supported abis.
     *
     * @return the string supported abis
     */
    public static String getStringSupportedABIS() {
        String result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] supportedABIS = Build.SUPPORTED_ABIS;
            StringBuilder supportedABIString = new StringBuilder();
            if (supportedABIS.length > 0) {
                for (String abis : supportedABIS) {
                    supportedABIString.append(abis).append(" ");
                }
                supportedABIString.deleteCharAt(supportedABIString.lastIndexOf(" "));
            } else {
                supportedABIString.append("");
            }
            result = supportedABIString.toString();
        }
        return TextUtils.isEmpty(result) ? "null" : result;
    }

    public static String getDeviceDetailInfo() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("APILevel: ").append(getSDKVersion()).append("\n");
        stringBuilder.append("BuildID: ").append(getBuildID()).append("\n");
        stringBuilder.append("OSVersion: ").append(getOSVersion()).append("\n");
        stringBuilder.append("Model: ").append(getModel()).append("\n");
        stringBuilder.append("Product: ").append(getProduct()).append("\n");
        stringBuilder.append("Device: ").append(getDevice()).append("\n");
        stringBuilder.append("Serial: ").append(getSerial()).append("\n");
        stringBuilder.append("DeviceBrand: ").append(getDeviceBrand()).append("\n");
        stringBuilder.append("SupportedABIS: ").append(getStringSupportedABIS()).append("\n");
        stringBuilder.append("Manufacturer: ").append(getManufacturer()).append("\n");

        return stringBuilder.toString();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isCharging(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent == null) {
            // should not happen
            return false;
        }

        // 0 is on battery
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC
                || plugged == BatteryManager.BATTERY_PLUGGED_USB
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);
    }

    @SuppressWarnings("deprecation")
    public static boolean isIdle(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*
             * isDeviceIdleMode() is a very strong requirement and could cause a job
             * to be never run. isDeviceIdleMode() returns true in doze mode, but jobs
             * are delayed until the device leaves doze mode
             */
            return powerManager.isDeviceIdleMode() || !powerManager.isInteractive();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return !powerManager.isInteractive();
        } else {
            return !powerManager.isScreenOn();
        }
    }

    public static boolean checkIfPowerSaverModeEnabled(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                return pm.isPowerSaveMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getDeviceImei(Context context) {
        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            imei = telephonyManager.getDeviceId();
        }
        return imei;
    }

    public static String getIpAddress(Context context){
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                return ipAddress;
            }  else if (info.getType() == ConnectivityManager.TYPE_ETHERNET){
                // 有限网络
                return getLocalIp();
            }
        }
        return "";
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    // 获取有限网IP
    private static String getLocalIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "0.0.0.0";

    }
}