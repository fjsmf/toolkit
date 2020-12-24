package ss.com.toolkit.ui.planeshoot.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.apkfuns.logutils.LogUtils;

public class Sprite {
    protected int cx, cy, x, y, width, height;
    private float speed;
    protected Bitmap bitmap;
    private boolean destroyed;
    private int hitCount;
    private int maxHit;

    protected Paint paint;

    public void onTick() {

    }

    public Sprite(Bitmap bitmap, int x, int y, float speed) {
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.x = x;
        this.y = y;
        cx = x + width / 2;
        cy = y + height / 2;
        this.speed = speed;
        paint = new Paint();
        maxHit = getMaxHit();
    }

    public int getMaxHit() {
        return 1;
    }

    public void draw(Canvas canvas) {
        y += speed * 5;
        cy += speed * 5;
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public void onDestroy() {
        destroyed = true;
        bitmap = null;
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public RectF getRect() {
        return new RectF(x, y, x + width, y + height);
    }

    public void plusHitCount(int hitCount) {
        this.hitCount += hitCount;
        if (this.hitCount >= maxHit) {
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getHitCount() {
        return hitCount;
    }
}
