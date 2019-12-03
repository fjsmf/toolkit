package ss.com.toolkit.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ss.com.toolkit.App;
import ss.com.toolkit.R;
import ss.com.toolkit.util.ScreenUtil;
import ss.com.toolkit.util.UIUtil;

import static android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT;
import static android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT;

/**
 * 通知栏管理
 */
public class NotificationManager {
    public static final String TAG = "NotificationManager";
    public int mTitleColor;
    public final String CHANNEL_CUSTOM = "custom";
    public final String CHANNEL_DEFAULT = "default";
    private Map<Long, Integer> mNotifyIdMap = new HashMap<>();
    private int mNotifyId = 0;
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_FRIEND_APPLY = 1;
    public static final int TYPE_GAME_INVITE = 2;
    public static final int TYPE_IM = 3;
    public static Set<String> msgIds = new HashSet<>();

    private static NotificationManager instance;

    public static NotificationManager getInstance() {
        if (instance == null) {
            synchronized (NotificationManager.class) {
                if (instance == null) {
                    instance = new NotificationManager();
                }
            }
        }
        return instance;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 显示自定义消息
     *
     * @param title
     * @param content
     */
    public void showCustomNotification(String title, String content, Intent intent) {
        try {
            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
                return;
            }
            Log.i(TAG, "推送收到的content:" + content + ", title:" + title);
            final android.app.NotificationManager notificationManager = (android.app.NotificationManager) App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            //建立一个RemoteView的布局，并通过RemoteView加载这个布局
            final RemoteViews remoteViews = new RemoteViews(App.getInstance().getPackageName(), R.layout.layout_push_content);
            int strokeWidth = 5; // 3dp 边框宽度
            int roundRadius = 15; // 8dp 圆角半径
            int strokeColor = Color.parseColor("#2E3135");//边框颜色
            int fillColor = Color.parseColor("#ff3f51b5");//内部填充颜色
            GradientDrawable gd = new GradientDrawable(LEFT_RIGHT, new int[]{0xff3f51b5, 0x00000000});//创建drawable
            gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
//            gd.setColor(fillColor);
//            gd.setColors(new int[]{0xff3f51b5,0x503f51b5, 0x00000000});
//            gd.setCornerRadius(roundRadius);
//            gd.setStroke(strokeWidth, strokeColor);


            remoteViews.setImageViewBitmap(R.id.iv_mask, UIUtil.convertToBitmap(gd, ScreenUtil.dp2px(200), ScreenUtil.dp2px(200)));
//            remoteViews.setInt(R.id.iv_mask, "setColorFilter", 0xff3f51b5);
            //跳转意图
            //设置PendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(App.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //将pendingIntent作为Notification的intent，这样当点击其他部分时，也能实现跳转
            remoteViews.setTextViewText(R.id.tv_push_content_content, content);
            remoteViews.setTextViewText(R.id.tv_push_tittle, title);
            if (content != null && content.length() > 50) {
                remoteViews.setTextViewTextSize(R.id.tv_push_content_content, TypedValue.COMPLEX_UNIT_DIP, 12);
            }
            final Notification notification = createNotification(remoteViews);
            if (isVersionO()) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_CUSTOM,
                        CHANNEL_CUSTOM, android.app.NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
            notification.when = System.currentTimeMillis();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.contentIntent = pendingIntent;
            notificationManager.notify((int) System.currentTimeMillis(), notification);
        } catch (Exception e) {
            Log.e(TAG, "展示推送出错:%s" + e.toString());
        }
    }

    private Notification createNotification(RemoteViews remoteViews) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getInstance(), CHANNEL_CUSTOM);
        builder.setSmallIcon(R.mipmap.ic_launcher_push);
        if (!isVersionSupport()) {
            if (isHtcBuild()) {
                builder.setContent(remoteViews);
            } else {
                builder.setCustomContentView(remoteViews);
                builder.setCustomBigContentView(remoteViews);
            }

            return builder.build();
        } else {
            if (isMeizuBuild()) {
                builder.setCustomContentView(remoteViews);
                builder.setContent(remoteViews);
                builder.setCustomBigContentView(remoteViews);
                return builder.build();
            }
            builder.setContent(remoteViews);
            builder.setCustomContentView(remoteViews);
            builder.setCustomBigContentView(remoteViews);
            builder.setOngoing(true);
            Notification notification = builder.build();
            return notification;
        }
    }

    public void showDefaultNotification(String title, final String content, Intent intent, int notifyId, boolean receiveByReceiver) {
        showDefaultNotification(title, content, intent, notifyId, receiveByReceiver, null);
    }

    public void showDefaultNotification(String title, final String content, Intent intent, int notifyId, boolean receiveByReceiver, Bitmap bitmap) {
        try {
            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
                return;
            }
            final android.app.NotificationManager notificationManager = (android.app.NotificationManager) App.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getInstance(), CHANNEL_DEFAULT);
            builder.setContentTitle(title);
            builder.setContentText(content);
            if (bitmap != null) {
                builder.setLargeIcon(bitmap);
            }
            builder.setSmallIcon(R.mipmap.ic_launcher_push);
//            builder.setSmallIcon(R.drawable.small_push_logo)
//                    .setColor(App.getInstance().getResources().getColor(R.color.ic_launcher_background));
            final Notification notification = builder.build();
            if (isVersionO()) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_DEFAULT, CHANNEL_DEFAULT, android.app.NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
            notification.when = System.currentTimeMillis();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            //设置PendingIntent
            PendingIntent pendingIntent;
            if (receiveByReceiver) {
                pendingIntent = PendingIntent.getBroadcast(App.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            } else {
                pendingIntent = PendingIntent.getActivity(App.getInstance(), (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            //将RemoteView作为Notification的布局
            //将pendingIntent作为Notification的intent，这样当点击其他部分时，也能实现跳转
            notification.contentIntent = pendingIntent;
            try {
                if (notificationManager != null) {
                    LogUtils.tag(TAG).d("notify :" + content);
                    notificationManager.notify(notifyId, notification);
                }
            } catch (Exception e) {
                Log.e(TAG, "弹通知栏出错:%s" + e.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "展示推送出错:%s" + e.toString());
        }
    }

    private boolean isVersionSupport() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    }

    private boolean isVersionO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    private boolean isMeizuBuild() {
        return Build.DISPLAY.contains("Flyme");
    }

    private boolean isHtcBuild() {
        return Build.BRAND.equals("htc");
    }

    public static boolean isMIUI() {

        return Build.MANUFACTURER.equals("xiaomi");
    }

    private int getNotifyId(long uid) {
        if (mNotifyIdMap.containsKey(uid)) {
            return mNotifyIdMap.get(uid);
        } else {
            mNotifyId++;
            mNotifyIdMap.put(uid, mNotifyId);
            return mNotifyId;
        }
    }

    private boolean isDarkNotificationTheme() {
        return !isSimilarColor(Color.BLACK, getNotificationColorInternal());
    }

    private int getNotificationColorInternal() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getInstance(), CHANNEL_DEFAULT);
        builder.setContentTitle(CHANNEL_DEFAULT);
        Notification notification = builder.build();
        try {
            ViewGroup notificationRoot = (ViewGroup) notification.contentView.apply(App.getInstance(), new FrameLayout(App.getInstance()));
            TextView title = notificationRoot.findViewById(android.R.id.title);
            if (title == null) {
                iteratorView(notificationRoot, new IFilter() {

                    @Override
                    public void filter(View view) {
                        if (view instanceof TextView) {
                            TextView textView = (TextView) view;
                            if (CHANNEL_DEFAULT.equals(textView.getText().toString())) {
                                mTitleColor = textView.getCurrentTextColor();
                            }
                        }
                    }
                });

                return mTitleColor;
            } else {
                return title.getCurrentTextColor();
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private void iteratorView(View view, IFilter filter) {
        if (view == null || filter == null) {
            return;
        }

        filter.filter(view);
        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;
            for (int i = 0, length = container.getChildCount(); i < length; i++) {
                View child = container.getChildAt(i);
                iteratorView(child, filter);
            }
        }
    }

    private interface IFilter {
        void filter(View view);
    }

    private boolean isSimilarColor(int baseColor, int color) {
        if (color == -1) {
            return true;
        }
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue);
        if (value < 180.0) {
            return true;
        }
        return false;
    }

    public static String get7chars(String str) {
        if (str == null || str.length() <= 7) {
            return str;
        }
        return str.substring(0, 7) + "...";
    }
}
