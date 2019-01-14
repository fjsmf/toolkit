package ss.com.toolkit.slidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ss.com.toolkit.R;

public class SideBarDemoActivity extends AppCompatActivity {
 private SideBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        bar= (SideBar) findViewById(R.id.bar);
//        bar.setScaleItemCount(1);
        bar.setOnStrSelectCallBack(new ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
          //      Toast.makeText(SideBarDemoActivity.this,selectStr,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
