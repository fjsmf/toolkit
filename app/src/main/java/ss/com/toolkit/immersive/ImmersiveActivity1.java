package ss.com.toolkit.immersive;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import ss.com.toolkit.R;
import ss.com.toolkit.util.ScreenUtil;

public class ImmersiveActivity1 extends Activity {

    ImageView iv_live;
    View commentContainer, tv_comment;

    CommentFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        LinearLayout llRoot = findViewById(android.R.id.ll_root);
//        // 设置根布局的paddingTop
//        llRoot.setPadding(0, getStatusBarHeight(this), 0, 0);
        setContentView(R.layout.activity_immersive1);
        initView();
    }

    private void initView() {
        iv_live = findViewById(R.id.iv_live);
        commentContainer = findViewById(R.id.comment_container);
        tv_comment = findViewById(R.id.tv_comment);
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    v.setSelected(false);
                    hideComment(true);
//                    hideComment(false);
                } else {
                    v.setSelected(true);
                    showComment(true);
//                    showComment(false);
                }
            }
        });
    }

    int durationOne = 200, durationTwo = 100;

    private void showComment(boolean isVideoFullScreen) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if ((fragment = (CommentFragment) fm.findFragmentByTag(CommentFragment.class.getName())) != null) {
            ft.show(fragment).commitAllowingStateLoss();
        } else {
            fragment = new CommentFragment();
            ft.add(R.id.comment_container, fragment, FragmentManager.class.getName()).commitAllowingStateLoss();
        }
        AnimatorSet set = new AnimatorSet();
        if (isVideoFullScreen) {
            float scale = (float) ScreenUtil.dp2px(211) / ScreenUtil.getScreenHeightPx();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_live, View.SCALE_X, 1, scale).setDuration(300);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_live, View.SCALE_Y, 1, scale).setDuration(300);
            iv_live.setPivotY((float) ScreenUtil.dp2px(44)/(1-scale));
            ObjectAnimator translationYComment = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, ScreenUtil.dp2px(commentContainer.getHeight()), 0).setDuration(300);
            set.playTogether(scaleX, scaleY, translationYComment);
            commentContainer.animate().translationY(-commentContainer.getHeight()).setDuration(300).start();
        } else {
            ObjectAnimator translationYCommentFirst = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, ScreenUtil.dp2px(commentContainer.getHeight()), 0).setDuration(durationOne);
            ObjectAnimator translationYCommentSecond = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, 0, -ScreenUtil.dp2px(157)).setDuration(durationTwo);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(iv_live, View.TRANSLATION_Y, 0, -ScreenUtil.dp2px(157)).setDuration(durationTwo);
            set.play(translationY).with(translationYCommentSecond).after(translationYCommentFirst);
        }
        set.start();
    }

    private void hideComment(boolean isVideoFullScreen) {
        commentContainer.animate().setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!tv_comment.isSelected()) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    if (fragment == null) {
                        fragment = (CommentFragment) fm.findFragmentByTag(CommentFragment.class.getName());
                    }
                    ft.hide(fragment).commitAllowingStateLoss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        if (isVideoFullScreen) {
            float scale = (float) ScreenUtil.dp2px(211) / ScreenUtil.getScreenHeightPx();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_live, View.SCALE_X, scale, 1f).setDuration(300);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_live, View.SCALE_Y, scale, 1f).setDuration(300);
            ObjectAnimator translationYComment = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, 0, ScreenUtil.dp2px(commentContainer.getHeight())).setDuration(300);
            set.playTogether(scaleX, scaleY, translationYComment);
        } else {
            ObjectAnimator translationYCommentFirst = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, -ScreenUtil.dp2px(157), 0).setDuration(durationTwo);
            ObjectAnimator translationYCommentSecond = ObjectAnimator.ofFloat(commentContainer, View.TRANSLATION_Y, 0, ScreenUtil.dp2px(commentContainer.getHeight())).setDuration(durationOne);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(iv_live, View.TRANSLATION_Y, -ScreenUtil.dp2px(157), 0).setDuration(durationTwo);

            set.play(translationYCommentFirst).with(translationY).before(translationYCommentSecond);
        }
        set.start();
    }

    @Override
    public void onBackPressed() {
        if (!fragment.isHidden()) {

        }
        super.onBackPressed();
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
