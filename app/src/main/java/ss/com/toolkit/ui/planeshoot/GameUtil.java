package ss.com.toolkit.ui.planeshoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class GameUtil {
    public static Bitmap getBitmapFromRes(Context context, int resId) {
        if (context == null) return null;
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static int generatePosition(int upLimit) {
        return new Random().nextInt(upLimit);
    }
}
