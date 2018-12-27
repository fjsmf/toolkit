import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ss.com.toolkit.net.IpInfo;

public class JavaTest {
    static Object o1 = new Object(), o2 = new Object();
    public static void main(String[] args) throws Exception {
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
/*
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
                o1.wait();
                System.out.println("end " + i);
            }
        }*/
    }


}
