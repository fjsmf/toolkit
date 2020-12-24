package ss.com.toolkit.ui.planeshoot.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.apkfuns.logutils.LogUtils;

public class Plane extends Sprite{

    public Plane(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y, 0);
    }

    public void updateCenterCoordinate(int x, int y) {
        this.cx = x;
        this.cy = y;
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        LogUtils.tag("plane").d("x :" + x + ", y:"+y);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
