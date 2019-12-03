package ss.com.toolkit.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import ss.com.toolkit.R;

/**
 * create by liuhuang at 2017/5/18
 */
public class PickRecyclerView extends RecyclerView {

    private LinearLayoutManager mLayoutManager;
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.6f;
    private float factor;
    private int childHei;


    private boolean onlyScaleMainView = true;

    OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int childCount = recyclerView.getChildCount();
            int heightCenter = recyclerView.getHeight() / 2;

            View snapView = mSnapHelper.findSnapView(mLayoutManager);

            for (int i = 0; i < childCount; i++) {
                ViewGroup childAt = (ViewGroup) recyclerView.getChildAt(i);
                int childCenter = childAt.getTop() + (childAt.getBottom() - childAt.getTop()) / 2;//child的中间线的y坐标

                int childToCenterDis = Math.abs(heightCenter - childCenter);//距离中间的距离
                if(childHei == 0){
                    childHei = getMeasuredHeight() / getChildCount();
                }

                if (onlyScaleMainView && childToCenterDis > childHei) {
                    childToCenterDis = childHei;
                }
                int delta = childToCenterDis;
                if (delta < 0) {
                    delta = 0;
                }
                float scale = (float) (Math.pow((delta - recyclerView.getHeight()), 2) * factor + MIN_SCALE);
                refreshView(snapView,childAt,scale);
            }

        }
    };
    private LinearSnapHelper mSnapHelper;

    /**
     * 调用此方法改写效果
     * @param snapView 选中的view
     * @param childAt 当前的view
     * @param scale 缩放倍数
     */
    private void refreshView(View snapView,ViewGroup childAt, float scale) {
        childAt.setScaleY(scale);
        childAt.setScaleX(scale);
        if(childAt!=snapView){
//            childAt.setBackgroundResource(R.drawable.list_bg);
        }else {
//            snapView.setBackgroundResource(R.drawable.list_bg_on);
        }
    }


    public PickRecyclerView(Context context) {
        this(context, null);
    }

    public PickRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //库24.2.0 才有的类
        mSnapHelper = new LinearSnapHelper();
        mSnapHelper.attachToRecyclerView(this);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        setLayoutManager(null);
        addOnScrollListener(mScrollListener);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        factor = (MAX_SCALE - MIN_SCALE) / (getMeasuredHeight() * getMeasuredHeight());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}