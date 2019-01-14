package ss.com.toolkit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java_.JavaLogUtil;
import rx.Observable;
import rx.Observer;
import ss.com.toolkit.device.DeviceActivity;
import ss.com.toolkit.location.LocationActivity;
import ss.com.toolkit.net.NetActivity;
import ss.com.toolkit.slidebar.SideBarDemoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View.OnUnhandledKeyEventListener a;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Observable.interval(0, 2, TimeUnit.SECONDS)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {
                            JavaLogUtil.log("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            JavaLogUtil.log("onError");
                        }

                        @Override
                        public void onNext(Long aLong) {
                            JavaLogUtil.log("onNext aLong:"+aLong);
                        }
                    });
            // 根据包名获取该app密钥散列
            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "com.huya.omhcg", PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String key =  new String(Base64.encode(md.digest(),0));
                    Log.d("initKeyhashs", key);
                }
            } catch (PackageManager.NameNotFoundException e) {

            } catch (NoSuchAlgorithmException e) {

            }
            if (Build.MODEL.toLowerCase().contains("vivo")) {
                Log.i("nadiee", "is vivo");
            }
            Log.i("nadiee", "system languege:" + getResources().getConfiguration().locale.getLanguage());
        });

        initView();
    }



    public void datePicker(View view) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        endDate.setTimeInMillis(System.currentTimeMillis());
        selectedDate.set(2008, 6, 6);
        startDate.set(1900, 0, 1);
//        endDate.set(2020,11,31);

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(getTime(date));
            }
        })
//                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Confirm")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("Title")//标题文字

                .setSubmitColor(0xff0d8bf5)//确定按钮文字颜色
                .setCancelColor(0xff0d8bf5)//取消按钮文字颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
//                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

      /*  // 日期选择器
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        endDate.setTimeInMillis(System.currentTimeMillis());
        selectedDate.set(2008, 6, 6);
        startDate.set(1900,0,1);
//        endDate.set(2020,11,31);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        }).setRangDate(startDate,endDate)//起始终止年月日设定
                .setDate(selectedDate)
                .setLabel("", "","","","","")
                .setContentTextSize(R.dimen.pickerview_textsize)
                .setTextColorCenter(0x666666)
                .setTextColorOut(0x999999)
                .build();*/
        pvTime.show();
    }

    public void killNetEasyMc() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses("com.mcbox.pesdk.launcher.apk");
//        Integer PID1= android.os.Process.getUidForName("com.android.email");
////        android.os.Process.killProcess(PID1);
    }

    private void initView() {
        RecyclerView recyclerv_view = findViewById(R.id.recyclerv_view);
        recyclerv_view.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerv_view.setAdapter(new RecyclerView.Adapter() {
            Item[] list = {
                    new Item("anim", AnimActivity.class),
                    new Item("net", NetActivity.class),
                    new Item("device", DeviceActivity.class),
                    new Item("location", LocationActivity.class),
                    new Item("slide", SideBarDemoActivity.class)
                };
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_item, null);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((MyViewHolder)viewHolder).txt.setText(list[i].name);
                viewHolder.itemView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, list[i].clazz)));
            }

            @Override
            public int getItemCount() {
                return list.length;
            }
        });
    }

    class Item{
        public String name;
        public Class<?> clazz;
        public Item(String name, Class<?> clazz){
            this.name = name;
            this.clazz = clazz;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
        }
    }

    private boolean executeCommand() {
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            java.lang.Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 47.74.184.234");
            int mExitValue = mIpAddrProcess.waitFor();
            Log.i("nadiee", "ping result : " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }

    public String Ping(String str) {
        String resault = "";
        java.lang.Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());

            if (status == 0) {
                resault = "ping success";
            } else {
                resault = "ping failed";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return resault;
    }

    public static String executeCmd(String cmd, boolean sudo) {
        try {

            java.lang.Process p;
            if (!sudo)
                p = Runtime.getRuntime().exec(cmd);
            else {
                p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
