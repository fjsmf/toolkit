package ss.com.toolkit.ui.planeshoot;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ss.com.toolkit.base.BaseActivity;

public class PlaneShootActivity extends BaseActivity {
    GameView gameView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameView = new GameView(this));
    }

    @Override
    protected void onDestroy() {
        gameView.onDestroy();
        super.onDestroy();
    }
}
