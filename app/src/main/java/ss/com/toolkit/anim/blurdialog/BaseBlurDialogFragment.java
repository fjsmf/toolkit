package ss.com.toolkit.anim.blurdialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;

import butterknife.ButterKnife;
import ss.com.toolkit.anim.blurview.Blurry;

public class BaseBlurDialogFragment extends DialogFragment {
    boolean started;
    int radius = 25, sampling = 5, color = Color.argb(180, 255, 255, 255);
    @Override
    public void onAttach(Context context) {
        setStyle(STYLE_NO_FRAME , 0);
        super.onAttach(context);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!started) {
            started = true;
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
            getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
            View backgroundView = getActivity().getWindow().getDecorView();
            Blurry.with(getActivity())
                    .radius(radius)
                    .sampling(sampling)
                    .color(color)
                    .capture(backgroundView)
                    .into(getDialog().getWindow().getDecorView());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setBlurRadius(int radius) {
        this.radius = radius;
    }

    public void setBlurSampling(int sampling) {
        this.sampling = sampling;
    }

    public void setBlurColor(int color) {
        this.color = color;
    }
}
