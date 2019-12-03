package ss.com.toolkit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    public static byte[] compress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = null;
        GZIPOutputStream gzip = null;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (out != null) out.close();
            } catch (IOException e) {
            }
            try {
                if (gzip != null) gzip.close();
            } catch (IOException e) {
            }
        }
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream ungzip = null;
        byte[] result = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(bytes);
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
            }
            try {
                if (out != null) out.close();
            } catch (IOException e) {
            }
            try {
                if (ungzip != null) ungzip.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

}
