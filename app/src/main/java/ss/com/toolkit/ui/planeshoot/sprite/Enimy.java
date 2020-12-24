package ss.com.toolkit.ui.planeshoot.sprite;

import android.graphics.Bitmap;

public class Enimy extends Sprite{

    public Enimy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y, 1);
    }

    @Override
    public int getMaxHit() {
        return 5;
    }
}
