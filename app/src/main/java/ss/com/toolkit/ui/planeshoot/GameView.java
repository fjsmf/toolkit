package ss.com.toolkit.ui.planeshoot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.apkfuns.logutils.LogUtils;

import ss.com.toolkit.R;

public class GameView extends View {
    private static final String TAG = "GameView";
    private GameController gameController;
    private boolean dragPlane;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        gameController = new GameController(context);
        gameController.setGameState(GameController.GAME_STATE_START);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (gameController.getGameState() == GameController.GAME_STATE_START) {
            drawStart(canvas);
        } else if (gameController.getGameState() == GameController.GAME_STATE_PAUSE) {
            drawPause(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickPause(event.getX(), event.getY())) {
            if (gameController.getGameState() == GameController.GAME_STATE_START) {
                gameController.setGameState(GameController.GAME_STATE_PAUSE);
            } else {
                gameController.setGameState(GameController.GAME_STATE_START);
            }
            invalidate();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameController.getGameRect().contains(event.getX(), event.getY())) {
                    dragPlane = true;
                    gameController.updataPlanePosition((int)event.getX(), (int)event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragPlane) {
                    gameController.updataPlanePosition((int)event.getX(), (int)event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                dragPlane = false;
                break;
        }
        return true;
    }

    private boolean isClickPause(float x, float y) {
        RectF pauseRecF = gameController.getSwitcherRect();
        LogUtils.tag(TAG).d("isClick rect:" + pauseRecF + ", x:" + x + ", y:" + y);
        return pauseRecF.contains(x, y);
    }


    private void drawPause(Canvas canvas) {
        // no need to draw
        drawStateSwitcher(canvas);
        gameController.draw(canvas);
    }

    private void drawStart(Canvas canvas) {
        drawStateSwitcher(canvas);
        gameController.draw(canvas);
        invalidate();
    }

    private void drawStateSwitcher(Canvas canvas) {
        if (gameController.getGameState() == GameController.GAME_STATE_START) {
            canvas.drawBitmap(GameUtil.getBitmapFromRes(getContext(), R.drawable.icon_im_item_stop), 20, 20, new Paint());
        } else {
            canvas.drawBitmap(GameUtil.getBitmapFromRes(getContext(), R.drawable.icon_im_item_play), 20, 20, new Paint());
        }
    }

    public void onDestroy() {
        gameController.onDestroy();
    }
}
