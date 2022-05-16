package java_;

import android.net.Uri;
import android.support.annotation.NonNull;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import ss.com.toolkit.net.IpInfo;

public class JavaTest {
    static Object o1 = new Object(), o2 = new Object();

    static final String HEXES = "0123456789abcdef";

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    class B {

    }

    public static String formatMessageTimeHHMM(long time) {
        Date date = new Date(time);
        return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date);
    }

    public static String formatMessageTimeHHMM() {
        Date date = new Date();
        return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date);
    }

    public static void main(String[] args) {
//        String url = "http://v-replay-hw.cdn.huya.com/record/huyalive/1259515661837-1259515661837-4682562792811659264-2519031447130-10057-A-0-1_2000/2021-04-28-11:30:05_2021-04-28-14:39:14.m3u8?bitrate=1953&client=81&definition=1300&pid=1259515661837&scene=vod&vid=498127429";
//        Uri uri = Uri.parse(url);
//        String vid = uri.getQueryParameter("vid");
//        String definition = uri.getQueryParameter("definition");
//        System.out.printf("vid:%s, definition:%s", vid, definition);

        String key = "[猜拳-2]";
        int num = Integer.parseInt(key.substring(key.lastIndexOf("-") + 1, key.indexOf("]")));

        System.out.println(key.matches("^\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+-[0-9]+\\]$") + ",---num:" + num);
        key = key.replaceAll("-[0-9]*", "");
        System.out.println(key);
        key = "勺子-122";
        System.out.println(key.matches("^[a-zA-Z0-9\\u4e00-\\u9fa5]+-[0-9]+$"));

        float a = 1 / 100f;
        DecimalFormat df = new DecimalFormat("0.00");
        String aaa = df.format(1 / 100f);
        double da = Double.parseDouble(aaa);
        int price = 1;
        double bigd = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("" + a + " -> double:" + aaa + ", da:" + da + ", BigD:" + bigd);

//        System.out.println("blockingSingle: " + Observable.just(1, 2, 3).blockingSingle(4));
//        long mUid = Long.MAX_VALUE;
//        System.out.println("MAX_VALUE:" + mUid);
//        System.out.println("1024:" + (int) ((mUid << 32 >> 32)));
//        System.out.println("1024:" + (int) (mUid >> 32));
//        System.out.println("mUid:" + (int) ((mUid << 32 >> 32) + (mUid >> 32)));
//        String json = "{\"23\":\"1\",\"24\":\"1\"}";
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            Map map = (Map) jsonObject;
//            LogUtils.d("giftFlagMap :" + map.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int i = 0;
//        while (i++ < 100) {
//            System.out.print(new Random().nextInt(3) + ", ");
//        }
        long mUid = 2147483646L;
//        System.out.println("MAX_VALUE:" + mUid);
//        System.out.println("1024:" + (int) ((mUid << 32 >> 32)));
//        System.out.println("1024:" + (int) (mUid >> 32));
//        System.out.println("mUid:" + (int) ((mUid << 32 >> 32) + (mUid >> 32)));
        ArrayList<Long> reqUids = new ArrayList<>();

        byte b = 1;
//        System.out.println("1<< 1 -> " + (int) (1L << 32));
//        System.out.println("1<< 1 -> " + (int) (b << 32));
//        for (long i = 1; i < 1002; i++) {
//            reqUids.add(i);
//        }
//        System.out.println("===[" + reqUids.get(0) + ", " + reqUids.get(reqUids.size() - 1) + "]");
//        for (int index = 0, count = (int) Math.ceil(reqUids.size() / 100.0); index < count; index++) {
//            ArrayList<Long> arrayList = new ArrayList<>();
//            arrayList.addAll(reqUids.subList(index * 100, index * 100 + 100 <= reqUids.size() ? index * 100 + 100 : reqUids.size()));
//            System.out.println("[" + arrayList.get(0) + ", " + arrayList.get(arrayList.size() - 1) + "], len:" + arrayList.size());
//        }
//        System.out.println(formatMessageTimeHHMM());
//        System.out.println(formatMessageTimeHHMM(System.currentTimeMillis() - 1000 * 60 * 12));
//        List<String> l1 = new ArrayList<>();
//        List<String> l2 = l1;
//        List<String> l3 = new ArrayList<>();
//        l3.add("a");
//        l1.addAll(l3);
//        System.out.println(l2);
//        boolean bbb = true;
//        boolean b1 = false;
//        bbb |= b1;
//        System.out.println(bbb);
//        List<String> lista = new ArrayList<>();
//        lista.add("a");
//        lista.add(1, "b");
//        lista.add(lista.size(), "c");
//        for (String s : lista) {
//            System.out.println(s);
//        }
//        Class cls = null;
//        try {
//            cls = Class.forName("java_.Hex");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            Hex b = (Hex) cls.newInstance();
//            System.out.println("hex : " + b);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//
//        byte[] bytes = "sunmaofei".getBytes();
//        StringBuilder sb = new StringBuilder();
//        char[] hexes = HEXES.toCharArray();
//        for (int i = 0, len = bytes.length, v; i < len; i++) {
//            v = bytes[i] & 0xff;
//            System.out.println("v : " + v);
//            System.out.println((v >>> 4) + " : " + hexes[v >>> 4]);
//            System.out.println((v & 0x0f) + " : " + hexes[v & 0x0f]);
//            sb.append(hexes[v >>> 4]);
//            sb.append(hexes[v & 0x0f]);
//        }
//        System.out.println(sb.toString());
//
//
//        System.out.println(7 << 4 & 0xf0);
//        String hexString = "73756e6d616f666569";
//        bytes = hexString.getBytes();
//        byte[] back = new byte[bytes.length / 2];
//        for (int i = 0, len = bytes.length / 2; i < len; i++) {
//            back[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
//        }
//        try {
//            System.out.println("back : " + new String(back, "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
////        System.out.println(byteArrayToHex("sunmaofei".getBytes()));
////        System.out.println(new BigInteger(1, "sunmaofei".getBytes()).toString(16));
////        System.out.println(getHex("sunmaofei".getBytes()));
////        System.out.println(bytesToHex("s".getBytes(), new char[200]));
//        System.out.println(toStringHex1("6e"));

        int cpuNums = Runtime.getRuntime().availableProcessors();  //获取当前系统的CPU 数目

//        newFixedThreadPool 和 newSingleThreadExecutor都是固定线程池数量

//        ExecutorService executorService = Executors.newFixedThreadPool(cpuNums * 2); //ExecutorService通常根据系统资源情


        /*cpu numbers:8
        0 start
        2 start
        1 start
        4 start
        3 start
        2 end
        0 end
        1 end
        5 start
        4 end
        7 start
        3 end
        6 start
        8 start
        9 start
        8 end
        5 end
        9 end
        6 end
        7 end*/
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        /*
        * 0 start
        0 end
        1 start
        1 end
        2 start
        2 end
        3 start
        3 end
        4 start
        4 end
        5 start
        5 end
        6 start
        6 end
        7 start
        7 end
        8 start
        8 end
        9 start
        9 end
        * */
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("cpu numbers:" + cpuNums);
        for (int i = 0; i < 10; i++) {
//            executorService.execute(new TestRunnable(i));
        }

        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("1 ");
                return "123";
            }
        });
        System.out.println(future.isCancelled());
//        future.cancel(true);
        System.out.println(future.isCancelled());
        try {
            System.out.println(future.get()); // cancel后抛异常
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("na ");
            }
        }, "nadiee");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable");
            }
        });
        executorService.shutdown();
        // Wait until all threads are finish
        try {
            executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        executor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("scheduleWithFixedDelay runnable" + System.currentTimeMillis());
                    }
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);
        executor.shutdown();
        System.out.println("main end");
    }

    static class TestRunnable implements Runnable {

        private int runnableIndex;

        public TestRunnable(int runnableIndex) {
            this.runnableIndex = runnableIndex;
        }

        @Override
        public void run() {
            System.out.println(runnableIndex + " start " + Thread.currentThread());
            try {
//                printThreads();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(runnableIndex + " end ");
        }

        private void printThreads() {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads);
            for (int i = 0; i < noThreads; i++) {
                System.out.println("线程号：" + i + " = " + lstThreads[i].getName());
            }
        }
    }

    private static String bytesToHex(@NonNull byte[] bytes, @NonNull char[] hexChars) {
        int v;
        char[] hex = HEXES.toCharArray();
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hex[v >>> 4];
            hexChars[j * 2 + 1] = hex[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    //转化字符串为十六进制编码
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    // 转化十六进制编码为字符串
    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    // 转化十六进制编码为字符串
    public static String toStringHex2(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
//    public static void main(String[] args) {
//        List<Integer> Test = new ArrayList<Integer>();
//        for (int i = 0; i < 10000000; i++) {
//            Test.add(i);
//        }
//        Long a1 = System.currentTimeMillis();
//        //普通的for
//        int size = Test.size();
//        for (int i = 0; i < size; i++) {
//            Integer integer = Test.get(i);
//        }
//        Long a2 = System.currentTimeMillis();
//        //for增强版
//        for (Integer integer : Test) { }
//        Long a3 = System.currentTimeMillis();
//        //iterator
//        Iterator<Integer> iterator = Test.iterator();
//        while (iterator.hasNext()){
//            Integer integer = iterator.next();
//        }
//        Long a4 = System.currentTimeMillis();
//        System.out.println("普通的for==================="+ (a2 - a1));
//        System.out.println("for增强版==================="+ (a3 - a2));
//        System.out.println("iterator==================="+ (a4 - a3));
//    }

    /*public static void main(String[] args) throws Exception {
        String aas = "aa";
        if (aas.equals(null)) {
            System.out.println(aas);
        } else {
            System.out.println("null");
        }
        List<Integer> listf = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listf.add(i);
        }
        for (int s : listf) {
            System.out.println(s);
        }

        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        System.out.println("test :" + df.format(10000/1000.0));
        System.out.println("test :" + df.format(12345/1000.0));
        System.out.println("test :" + df.format(12341234/1000000.0));
        System.out.println(Math.ceil(10000/1000.0));
        System.out.println(Math.ceil(12345/1000.0));
        System.out.println(Math.ceil(12341234/1000.0));
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("1", "111");
        hm.put("2", "222");
        hm.put("3", "333");
        Set<Map.Entry<String, String>> entrySet = hm.entrySet();
        Iterator<Map.Entry<String, String>> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        List<String> arrayList = new ArrayList<>();
        int gcounter = 0;
        while (gcounter++ < 15000000) {
            arrayList.add(String.valueOf(gcounter));
        }
        String item;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < arrayList.size(); ++i) {
            item = arrayList.get(i);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("escape time 1:" + (t2 - t1));

        for (int i = 0, len = arrayList.size(); i < len; ++i) {
            item = arrayList.get(i);
        }
        System.out.println("escape time 2:" + (System.currentTimeMillis() - t2));
        long t3 = System.currentTimeMillis();
        for (String item1 : arrayList) {

        }
        long t4 = System.currentTimeMillis();
        System.out.println("escape time 3:" + (t4 - t3));
        Iterator<String> itr = arrayList.iterator();
        while (itr.hasNext()) {
            item = itr.next();
        }
        long t5 = System.currentTimeMillis();
        System.out.println("escape time 4:" + (t5 - t4));


        String fb = "{\"status\":1}";
        if (fb.contains("\"status\":1")) {

        }


        long time = System.currentTimeMillis();
        System.out.println("int long : " + (int) time + ", long:" + time);
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            System.out.println(random.nextInt(4));
        }
        String aa = "aldjfkldgjkfdgjdflgjldfgjdfklgjdflgjdflkgjdf";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000L; i++) {
//            aa.substring(aa.length()/2, aa.length()/2 + 2);
//            try {
            aa.substring(aa.length() / 2, aa.length() / 2 + 2);
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
            aa.substring(aa.length() / 2, aa.length() / 2 + 2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        end = System.currentTimeMillis();
        System.out.println(String.format(Locale.US, "catch: start:%d, end:%d, escape:%d", start, end, end - start));
        // 冒泡排序
        int[] arr = {100, 12, -3, 2, 4, 0, 8, 1, 76, 33, 11, 55, 22, 1, 0};
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


        System.out.println("long : " + (long) Math.ceil(1100 / 1000.0));
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("b");
        list.remove("b");
        list.remove("b");
        list.remove(null);
        System.out.println("size:" + list.size());
        System.out.println(Math.round(3100 / 1000.0));
        System.out.println(Math.round(3500 / 1000.0));
        System.out.println(Math.round(4900 / 1000.0));
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
    }*/

    public static void testIpPattern() {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testWaitNotiy() {
        for (int i = 0; i < 10; i++) {
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
                        JavaLogUtil.log("onNext aLong:" + aLong);
                    }
                });
    }

    public static void testObservable() {
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                JavaLogUtil.log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                JavaLogUtil.log("onError :" + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                JavaLogUtil.log("onNext : " + o.toString());
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
                JavaLogUtil.log("Subscriber onError : " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                if (!isUnsubscribed()) {
                    JavaLogUtil.log("Subscriber onNext : " + o.toString());
                }
                unsubscribe();
            }
        });

    }

}
