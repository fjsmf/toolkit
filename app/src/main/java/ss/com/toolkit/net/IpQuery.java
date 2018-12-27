package ss.com.toolkit.net;

import android.content.Context;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class IpQuery {
    public static Observable<IpInfo> queryFromIp138() {
        return new Retrofit.Builder()
                .baseUrl("http://www.ip138.com/")
                .addConverterFactory(Ip138ConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(IIpQuery.class)
                .queryFromIp138()
                .subscribeOn(Schedulers.io());
    }

    public static Observable<IpInfo> queryFromIfconfigMe() {
        return new Retrofit.Builder()
                .baseUrl("https://ifconfig.me/")
                .addConverterFactory(IfconfigMeConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(IIpQuery.class)
                .queryFromIfconfigMe()
                .subscribeOn(Schedulers.io());
    }

    public static class Ip138ConverterFactory extends Converter.Factory {
        public static Ip138ConverterFactory create() {
            return new Ip138ConverterFactory();
        }

        public static void printGroup(Matcher result) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= result.groupCount(); i++) {
                sb.append("【").append(result.group(i)).append("】, ");
            }
            Log.i("nadiee", sb.toString());
        }

        @Override
        public Converter<ResponseBody, IpInfo> responseBodyConverter(Type type,
                                                                     Annotation[] annotations,
                                                                     Retrofit retrofit) {
            return value -> {
                try {
                    final String response = new String(value.bytes(), "gb2312").replaceAll("\r", "").replaceAll("\n", "");
                    final Pattern pattern = Pattern.compile(".+您的IP地址是：\\[(.+)\\] 来自：([^ ]+) (.*?)<.+");
                    final Matcher matcher = pattern.matcher(response);
                    if (matcher.find()) {
                        final IpInfo ipInfo = new IpInfo();
                        if (matcher.groupCount() == 3) {
                            ipInfo.ip = matcher.group(1);
                            ipInfo.region = matcher.group(2);
                            ipInfo.provider = matcher.group(3);
                            return ipInfo;
                        }
                    }
                }catch (Exception e) {

                }
                return null;
            };
        }

        @Override
        public Converter<IpInfo, RequestBody> requestBodyConverter(Type type,
                                                                   Annotation[] parameterAnnotations,
                                                                   Annotation[] methodAnnotations, Retrofit retrofit) {
            return null;
        }
    }

    public static class IfconfigMeConverterFactory extends Converter.Factory {
        public static IfconfigMeConverterFactory create() {
            return new IfconfigMeConverterFactory();
        }

        public static void printGroup(Matcher result) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= result.groupCount(); i++) {
                sb.append("【").append(result.group(i)).append("】, ");
            }
            Log.i("nadiee", sb.toString());
        }

        @Override
        public Converter<ResponseBody, IpInfo> responseBodyConverter(Type type,
                                                                     Annotation[] annotations,
                                                                     Retrofit retrofit) {
            return value -> {
                try {//您的IP地址是：[113.106.251.87] 来自：广东省珠海市 电信  <td id="ip_address_cell"><strong id="ip_address">202.181.149.5</strong></td>
                    final String response = new String(value.bytes(), "gb2312").replaceAll("\r", "").replaceAll("\n", "");
//                    final Pattern pattern = Pattern.compile(".+您的IP地址是：\\[(.+)\\] 来自：([^ ]+) (.*?)<.+");
                    final Pattern pattern = Pattern.compile("id=\"ip_address\">(?<=(\\b|\\D))(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))");
                    final Matcher matcher = pattern.matcher(response);
                    if (matcher.find()) {
                        final IpInfo ipInfo = new IpInfo();
                        ipInfo.ip = matcher.group();
                        ipInfo.ip = ipInfo.ip.substring(16);
                        System.out.println(ipInfo.ip);
                        return ipInfo;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };
        }

        @Override
        public Converter<IpInfo, RequestBody> requestBodyConverter(Type type,
                                                                   Annotation[] parameterAnnotations,
                                                                   Annotation[] methodAnnotations, Retrofit retrofit) {
            return null;
        }
    }

    private static String[] platforms = {
            "http://pv.sohu.com/cityjson",
            "http://pv.sohu.com/cityjson?ie=utf-8",
            "http://ip.chinaz.com/getip.aspx"
    };
    public static String getOutNetIP(Context context, int index) {
        if (index < platforms.length) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(platforms[index]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);//读取超时
                urlConnection.setConnectTimeout(5000);//连接超时
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                    Log.e("xiaoman", builder.toString());
                    if (index == 0 || index == 1) {
                        //截取字符串
                        int satrtIndex = builder.indexOf("{");//包含[
                        int endIndex = builder.indexOf("}");//包含]
                        String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                        JSONObject jo = new JSONObject(json);
                        String ip = jo.getString("cip");

                        return ip;
                    } else if (index == 2) {
                        JSONObject jo = new JSONObject(builder.toString());
                        return jo.getString("ip");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            return getInNetIp(context);
        }
        return getOutNetIP(context, ++index);
    }
}
