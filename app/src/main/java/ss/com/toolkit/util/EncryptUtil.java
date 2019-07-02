package ss.com.toolkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static String encryptFileMD5(String path) {
        File file = new File(path);
        if (file.isDirectory() || !file.exists()) {
            return "";
        }
        return encryptFileMD5(file);
    }

    public static String encryptFileMD5(File inputFile) {
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;

        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) {
            }
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return bytesToHexString(resultByteArray);
        } catch (IOException | NoSuchAlgorithmException e) {
            return "";
        } finally {
            try {
                if (digestInputStream != null) {
                    digestInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param b to hexstring
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String bytesToHexString(byte[] b) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (b != null) {
            for (int i = 0; i < b.length; i++) {
                sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
        }
        return sb.toString();
    }


    /**
     * string to byte use UTF-8
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] StringToBytes(String data) throws UnsupportedEncodingException {
        return data.getBytes("UTF-8");
    }
}
