package ss.com.toolkit.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.toolkit.R;
import ss.com.toolkit.model.Api;
import ss.com.toolkit.model.Response.AddFeedBackRsp;
import ss.com.toolkit.model.Response.IsNeedUploadLogRsp;
import ss.com.toolkit.model.Response.LogUploadRangeRsp;
import ss.com.toolkit.retrofit.RetrofitManager;

public class NetActivity extends AppCompatActivity {
    private EditText et_cmd;
    private String app_path;
    private TextView tv_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        /*初始化控件*/
        et_cmd = (EditText) findViewById(R.id.et_cmd);
        tv_result = (TextView) findViewById(R.id.tv_result);
        /* 获取app安装路径 */
        app_path = getApplicationContext().getFilesDir().getAbsolutePath();
        LogManager.getInstance().zipAndUploadLog();
//        addFeedBack();
    }

    @SuppressLint("CheckResult")
    public void queryIp(View view) {
        IpQuery.queryFromIfconfigMe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ipInfo ->
                                Log.i("nadiee", String.format("ip:%s, region:%s, provider:%s", ipInfo.ip, ipInfo.region, ipInfo.provider == null ? "" : ipInfo.provider)),
                        err -> {
                            Log.i("nadiee", "request ip info error :" + err.getMessage());
                            err.printStackTrace();
                        });
    }

    /**
     * 按钮点击事件
     */
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.copy_busybox: /* 拷贝busybox可执行文件 */
                varifyFile(getApplicationContext(), "busybox");
                break;
            case R.id.copy_traceroute:/* 拷贝traceroute可执行文件 */
                varifyFile(getApplicationContext(), "traceroute");
                break;
            case R.id.exe_busybox:/* 将busybox命令添加到Editext中 */
                String cmd = "." + app_path + "/busybox";
                System.out.println(et_cmd);
                et_cmd.setText(cmd);
                break;
            case R.id.exe_traceroute:/* 将traceroute命令添加到Editext中 */
                cmd = "." + app_path + "/traceroute 8.8.8.8";
                et_cmd.setText(cmd);
                break;
            case R.id.exe: /* 执行Editext中的命令 */
                cmd = et_cmd.getText().toString();
                /* 执行脚本命令 */
                List<String> results = exe(cmd);
                String result = "";
                /* 将结果转换成字符串, 输出到 TextView中 */
                for (String line : results) {
                    result += line + "\n";
                }
                tv_result.setText(result);
                break;
            case R.id.ping:
//                result = executeCmd("ping -c 1 -w 1 www.baidu.com", false);
                Schedulers.io().scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        String result = executeCmd("ping -c 1 -w 1 www.baidu.com", false);
                        LogUtils.tag("nadiee").i("result:"+result);
                    }
                });
            default:
                break;
        }
    }

    public void addFeedBack() {
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogapp.huya.com")
                .getAdInfo("sstest", "长连接异常", "123", "2", "6000")
                .enqueue(new Callback<AddFeedBackRsp>() {
                    @Override
                    public void onResponse(Call<AddFeedBackRsp> call, Response<AddFeedBackRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());
                    }
                    @Override
                    public void onFailure(Call<AddFeedBackRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogapp.huya.com")
                .isNeedUploadLog("1", "123", "2", "6000")
                .enqueue(new Callback<IsNeedUploadLogRsp>() {
                    @Override
                    public void onResponse(Call<IsNeedUploadLogRsp> call, Response<IsNeedUploadLogRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());
                    }
                    @Override
                    public void onFailure(Call<IsNeedUploadLogRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogupload-an.huya.com")
                .getRemoteFileRange("1911")
                .enqueue(new Callback<LogUploadRangeRsp>() {
                    @Override
                    public void onResponse(Call<LogUploadRangeRsp> call, Response<LogUploadRangeRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());
                    }
                    @Override
                    public void onFailure(Call<LogUploadRangeRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
        /*RetrofitManager.getInstance().get(Api.class, "http://ffilelogupload-an.huya.com")
                .uploadLog("1", "123", "2", "6000")
                .enqueue(new Callback<IsNeedUploadLogRsp>() {
                    @Override
                    public void onResponse(Call<IsNeedUploadLogRsp> call, Response<IsNeedUploadLogRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());
                    }
                    @Override
                    public void onFailure(Call<IsNeedUploadLogRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });*/
    }

    public static String executeCmd(String cmd, boolean sudo){
        try {
            java.lang.Process p;
            if(!sudo)
                p= Runtime.getRuntime().exec(cmd);
            else{
                p= Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
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

    /**
     * 验证文件是否存在, 如果不存在就拷贝
     */
    private void varifyFile(Context context, String fileName) {
        try {
            /* 查看文件是否存在, 如果不存在就会走异常中的代码 */
            context.openFileInput(fileName);
        } catch (FileNotFoundException notfoundE) {
            try {
                /* 拷贝文件到app安装目录的files目录下 */
                copyFromAssets(context, fileName, fileName);
                /* 修改文件权限脚本 */
                String script = "chmod 700 " + app_path + "/" + fileName;
                /* 执行脚本 */
                exe(script);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文件从assets目录中拷贝到app安装目录的files目录下
     */
    private void copyFromAssets(Context context, String source,
                                String destination) throws IOException {
        /* 获取assets目录下文件的输入流 */
        InputStream is = context.getAssets().open(source);
        /* 获取文件大小 */
        int size = is.available();
        /* 创建文件的缓冲区 */
        byte[] buffer = new byte[size];
        /* 将文件读取到缓冲区中 */
        is.read(buffer);
        /* 关闭输入流 */
        is.close();
        /* 打开app安装目录文件的输出流 */
        FileOutputStream output = context.openFileOutput(destination,
                Context.MODE_PRIVATE);
        /* 将文件从缓冲区中写出到内存中 */
        output.write(buffer);
        /* 关闭输出流 */
        output.close();
    }

    /**
     * 执行 shell 脚本命令
     */
    private List<String> exe(String cmd) {
        /* 获取执行工具 */
        Process process = null;
        /* 存放脚本执行结果 */
        List<String> list = new ArrayList<String>();
        try {
            /* 获取运行时环境 */
            Runtime runtime = Runtime.getRuntime();
            /* 执行脚本 */
            process = runtime.exec(cmd);
            /* 获取脚本结果的输入流 */
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            /* 逐行读取脚本执行结果 */
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
