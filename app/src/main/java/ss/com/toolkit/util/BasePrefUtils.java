package ss.com.toolkit.util;

import android.content.Context;
import android.content.SharedPreferences;


public class BasePrefUtils {
    private static final String REGION_COOKIE_PREF = "region_cookie_pref";
    private final static String KEY_REGION = "region";
    private final static String KEY_LANGUAGE = "language";
    private final static String KEY_PUSH_TOKEN = "msgRegToken";

    private final static String LANGUAGE_NAME = "language_name";
    private final static String REGION_NAME = "region_name";

    private static final String GLOBAL_GAME_INFO = "global_game_info";
    //玩过的游戏数量
    public static final String PLAYED_GAME_NUMBER = "played_game_number";
    //游戏总时长
    public static final String PLAYED_GAME_TOTAL_TIME = "played_game_total_time";
    private final static String KEY_COUNTRY = "country";


    public static void savaCookieRegion(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_REGION, value).apply();
    }

    public static String getCookieRegion(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(KEY_REGION, "");
    }

    public static void savaCookieLanguage(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_LANGUAGE, value).apply();
    }

    public static String getCookieLanguage(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(KEY_LANGUAGE, "en");
    }

    public static void savaLanguageName(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(LANGUAGE_NAME, value).apply();
    }

    public static String getLanguageName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(LANGUAGE_NAME, "");
    }

    public static void savaRegionName(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(REGION_NAME, value).apply();
    }

    public static String getRegionName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(REGION_NAME, "");
    }

    public static void savaGameNumber(Context context, String userId, int value) {
        SharedPreferences sp = context.getSharedPreferences(GLOBAL_GAME_INFO, Context.MODE_PRIVATE);
        sp.edit().putInt(PLAYED_GAME_NUMBER + userId, value).apply();
    }

    public static int getGameNumber(Context context, String userId) {
        SharedPreferences sp = context.getSharedPreferences(GLOBAL_GAME_INFO, Context.MODE_PRIVATE);
        return sp.getInt(PLAYED_GAME_NUMBER + userId, 0);
    }

    public static void savaGameTotalTime(Context context, String userId, long value) {
        SharedPreferences sp = context.getSharedPreferences(GLOBAL_GAME_INFO, Context.MODE_PRIVATE);
        sp.edit().putLong(PLAYED_GAME_TOTAL_TIME + userId, value).apply();
    }

    public static long getGameTotalTime(Context context, String userId) {
        SharedPreferences sp = context.getSharedPreferences(GLOBAL_GAME_INFO, Context.MODE_PRIVATE);
        return sp.getLong(PLAYED_GAME_TOTAL_TIME + userId, 0L);
    }


    public static void savePushToken(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PUSH_TOKEN, value).apply();
    }

    public static String getPushToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(KEY_PUSH_TOKEN, "");
    }

    public static void saveCountry(Context context, String country) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_COUNTRY, country).apply();
    }

    public static String getCountry(Context context) {
        SharedPreferences sp = context.getSharedPreferences(REGION_COOKIE_PREF, Context.MODE_PRIVATE);
        return sp.getString(KEY_COUNTRY, "");
    }

}
