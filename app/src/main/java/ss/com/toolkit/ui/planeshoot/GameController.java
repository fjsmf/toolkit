package ss.com.toolkit.ui.planeshoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ss.com.toolkit.R;
import ss.com.toolkit.ui.planeshoot.sprite.Bullet;
import ss.com.toolkit.ui.planeshoot.sprite.Enimy;
import ss.com.toolkit.ui.planeshoot.sprite.Plane;
import ss.com.toolkit.ui.planeshoot.sprite.Sprite;
import ss.com.toolkit.util.ScreenUtil;

public class GameController {
    public final static int SPRITE_SWITCHER_PLAY = 1;
    public final static int SPRITE_SWITCHER_PAUSE = 2;
    public final static int SPRITE_ENEMY = 3;
    public final static int SPRITE_PLANE = 4;
    public final static int SPRITE_BULLET = 5;

    public final static int GAME_STATE_START = 1;
    public final static int GAME_STATE_PAUSE = 2;
    public final static int GAME_STATE_DESTROY = 3;

    private SparseArray<Bitmap> spritesBitmap;
    private List<Sprite> sprites;
    private List<Sprite> bullets;
    private Context context;
    private int gameState;
    private long frame;
    private Plane plane;
    private RectF gameRect, switcherRect, planeRect;

    public GameController(Context context) {
        this.context = context;
        init();
    }

    public void init() {
        spritesBitmap = new SparseArray<>();
        sprites = new ArrayList<>();
        bullets = new ArrayList<>();
        spritesBitmap.put(SPRITE_SWITCHER_PLAY, GameUtil.getBitmapFromRes(context, R.drawable.icon_im_item_play));
        spritesBitmap.put(SPRITE_SWITCHER_PAUSE, GameUtil.getBitmapFromRes(context, R.drawable.icon_im_item_stop));
        spritesBitmap.put(SPRITE_ENEMY, GameUtil.getBitmapFromRes(context, R.drawable.middle_plane));
        spritesBitmap.put(SPRITE_PLANE, GameUtil.getBitmapFromRes(context, R.drawable.plane));
        spritesBitmap.put(SPRITE_BULLET, GameUtil.getBitmapFromRes(context, R.drawable.yellow_bullet));
        sprites.add(generateSprite(SPRITE_ENEMY));
        plane = (Plane) generateSprite(SPRITE_PLANE);
    }

    public Sprite generateSprite(int spriteType) {
        if (spriteType < 1 || spriteType > 5) {
            throw new IllegalArgumentException();
        }
        switch (spriteType) {
            case SPRITE_ENEMY:
                return new Enimy(spritesBitmap.get(spriteType), GameUtil.generatePosition(ScreenUtil.getScreenWidthPx() - 100), 0);
            case SPRITE_PLANE:
                return new Plane(spritesBitmap.get(spriteType), 0, ScreenUtil.getScreenHeightPx() - 300);
            case SPRITE_BULLET:
                return new Bullet(spritesBitmap.get(spriteType), plane.getCx(), plane.getCy() - 100);
        }
        return null;
    }

    public void draw(Canvas canvas) {
        if (gameState == GAME_STATE_START) {
            if (frame++ % 50 == 0) {
                sprites.add(generateSprite(SPRITE_ENEMY));
            }
            if (frame % 10 == 0) {
                bullets.add(generateSprite(SPRITE_BULLET));
            }
        }
        Iterator<Sprite> itr = sprites.iterator();
        List<Sprite> toDel = new ArrayList<>();
        List<Sprite> toDelBullets = new ArrayList<>();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();

            if (plane.getRect().intersect(sprite.getRect())) {
                sprite.plusHitCount(1);
                gameState = GAME_STATE_DESTROY;
            }
            for (Sprite s : bullets) {
                if (s.getRect().intersect(sprite.getRect())) {
                    toDelBullets.add(s);
                    s.plusHitCount(1);
                    sprite.plusHitCount(1);
                }
            }
            if (sprite.isDestroyed()) {
                toDel.add(sprite);
            }
            if (!sprite.isDestroyed()) {
                sprite.draw(canvas);
            }
        }
        plane.draw(canvas);
        bullets.removeAll(toDelBullets);
        for (Sprite s : bullets) {
            s.draw(canvas);
        }
        sprites.removeAll(toDel);
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
    
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
    
    public int getGameState() {
        return gameState;
    }

    public RectF getSwitcherRect() {
        if (switcherRect != null) return switcherRect;
        Bitmap bitmap = spritesBitmap.get(SPRITE_SWITCHER_PLAY);
        return switcherRect = new RectF(ScreenUtil.dp2px(15), ScreenUtil.dp2px(15), bitmap.getWidth() + ScreenUtil.dp2px(15), bitmap.getHeight() + ScreenUtil.dp2px(15));
    }

    public RectF getGameRect() {
        if (gameRect != null) return gameRect;
        return gameRect = new RectF(0, 0, ScreenUtil.getScreenWidthPx(),  ScreenUtil.getScreenHeightPx());
    }

    public void updataPlanePosition(int x, int y) {
        plane.updateCenterCoordinate(x, y);
    }

    public void onDestroy() {
        context = null;
        if (spritesBitmap != null) {
            for (int i = 0, len = spritesBitmap.size(); i < len; i++) {
                Bitmap bitmap = spritesBitmap.get(i);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            spritesBitmap.clear();
        }
        if (sprites != null) {
            for (Sprite sprite : sprites) {
                sprite.onDestroy();
            }
            sprites.clear();
        }
    }
    
}
