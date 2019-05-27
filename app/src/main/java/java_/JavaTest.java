package java_;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import ss.com.toolkit.net.IpInfo;

public class JavaTest {
    static Object o1 = new Object(), o2 = new Object();
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            System.out.println(random.nextInt(4));
        }
        String aa = "aldjfkldgjkfdgjdflgjldfgjdfklgjdflgjdflkgjdf";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000L; i++) {
//            aa.substring(aa.length()/2, aa.length()/2 + 2);
//            try {
                aa.substring(aa.length()/2, aa.length()/2 + 2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format(Locale.US, "catch: start:%d, end:%d, escape:%d", start, end, end - start));

        aa = "aldjfkldgjkfdgjdflgjldfgjdfklgjdflgjdflkgjdf";
        start = System.currentTimeMillis();
        for (int j = 0; j < 100000L; j++) {
//            try {
                aa.substring(aa.length()/2, aa.length()/2 + 2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        end = System.currentTimeMillis();
        System.out.println(String.format(Locale.US, "catch: start:%d, end:%d, escape:%d", start, end, end - start));
        // 冒泡排序
        int[] arr = {100,12,-3, 2,4,0,8,1,76,33,11,55,22,1,0};
        for (int i = 0, len = arr.length; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) { // 相邻元素两两对比
                    int temp = arr[j + 1]; // 元素交换
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i).append("  ");
        }
        System.out.println(sb);
        String a = null, b = "";
        if (b.equals(a)) {
            System.out.println("equals");
        } else {
            System.out.println("not equals");
        }

        String s = "\\ud83d\\ude00";
        System.out.println(s.replace("\\", "\\"));
        System.out.println("str2HexStr :" + Hex.str2HexStr("\ud83d\ude23"));
        String content = "[ud83dude23][ud83dude23]ud83dude23]";
        String emojiStr = "\ud83d\ude23][ud83dude23]ud83dude23]";
//        String zhengze = "\\[u[^\\]]+u[^\\]]+\\]";
        String zhengze = "(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?)";
        // 通过传入的正则表达式来生成一个pattern
        Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patten.matcher(content);
        while (matcher.find()) {
            String key = matcher.group();
            System.out.println("key :" + key);
        }


        System.out.println("long : " + (long)Math.ceil(1100/1000.0));
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("b");
        list.remove("b");
        list.remove("b");
        list.remove(null);
        System.out.println("size:" + list.size());
        System.out.println(Math.round(3100/1000.0));
        System.out.println(Math.round(3500/1000.0));
        System.out.println(Math.round(4900/1000.0));
        testRxTimer();
        testObservable();

        Subscription subscription = Observable.just("Hello subscription")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
    public static void testIpPattern(){
        boolean b = Pattern.matches("^[0-9]*$", "111$11");
        System.out.println(b);

        final String response = new String("<td id=\"ip_address_cell\"><strong id=\"ip_address\">202.181.149.5</strong></td>").replaceAll("\r", "").replaceAll("\n", "");
//                    final Pattern pattern = Pattern.compile("id=\"ip_address\">^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");
        final Pattern pattern = Pattern.compile("id=\"ip_address\">(?<=(\\b|\\D))(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))");
        final Matcher matcher = pattern.matcher(response);
        try {
            if (matcher.find()) {
                final IpInfo ipInfo = new IpInfo();
                ipInfo.ip = matcher.group();
                ipInfo.ip = ipInfo.ip.substring(16);
                System.out.println(ipInfo.ip);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void testWaitNotiy(){
        for(int i = 0; i < 10; i++){
            synchronized (o1) {
                System.out.println("start " + i);
                new Thread() {
                    @Override
                    public void run() {
                        synchronized (o1) {
                            try {
                                o2.wait(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            o1.notify();
                        }
                    }
                }.start();
                try {
                    o1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end " + i);
            }
        }
    }

    public static void testRxTimer() {
        JavaLogUtil.log("testRxTimer");
//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        JavaLogUtil.log("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        JavaLogUtil.log("onError");
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        JavaLogUtil.log("onNext aLong:"+aLong);
//                    }
//                });
        Observable.interval(2, 2, TimeUnit.SECONDS)
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
    }

    public static void testObservable() {
        Observer observer = new Observer(){
            @Override
            public void onCompleted() {
                JavaLogUtil.log("onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                JavaLogUtil.log("onError :"+e.getMessage());
            }
            @Override
            public void onNext(Object o) {
                JavaLogUtil.log("onNext : "+o.toString());
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 8; i++) {
                        int temp = new Random().nextInt(10);
                        if (temp > 8) {
                            //if value>8, we make an error
                            subscriber.onError(new Throwable("value >8"));
                            break;
                        } else {
                            subscriber.onNext(temp);
                        }
                        // on error,complete the job
                        if (i == 3) {
                            subscriber.onCompleted();
                        }
                    }
                }
            }
        });
        observable.subscribe(observer);
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                JavaLogUtil.log("Subscriber onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                JavaLogUtil.log("Subscriber onError : "+e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                if (!isUnsubscribed()){
                    JavaLogUtil.log("Subscriber onNext : "+o.toString());
                }
                unsubscribe();
            }
        });

    }

}
