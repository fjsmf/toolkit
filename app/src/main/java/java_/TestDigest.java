package java_;

public class TestDigest {
    public static void main(String[] args) {
        TestDigest my = new TestDigest();
        my.testDigest();
    }

    public void testDigest() {
        try {
            String myinfo = "ALICE,I MISS YO";
            //java.security.MessageDigest alg=java.security.MessageDigest.getInstance("MD5");
            java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
            alga.update(myinfo.getBytes());
            byte[] digesta = alga.digest();
            System.out.println("alga : SHA-1,本信息摘要是:" + byte2hex(digesta));
            //通过某中方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
            java.security.MessageDigest algb = java.security.MessageDigest.getInstance("SHA-256");
            algb.update(myinfo.getBytes());
            System.out.println("alga : SHA-256,本信息摘要是:" + byte2hex(algb.digest()));
            if (algb.isEqual(digesta, algb.digest())) {
                System.out.println("信息检查正常");
            } else {
                System.out.println("摘要不相同");
            }
        } catch (java.security.NoSuchAlgorithmException ex) {
            System.out.println("非法摘要算法");
        }
    }

    public String byte2hex(byte[] b) { //二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }
}