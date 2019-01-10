package java_;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import ss.com.toolkit.net.IpInfo;

public class JavaTest {
    static Object o1 = new Object(), o2 = new Object();
    public static void main(String[] args) throws Exception {
        testRxTimer();
        testObservable();
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
