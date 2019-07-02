package ss.com.toolkit.util;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

// copy from https://raw.githubusercontent.com/mcxiaoke/Android-Next/master/core/src/main/java/com/mcxiaoke/next/utils/ZipUtils.java
public class ZipUtils {
    private ZipUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int BUFFER = 1024;

    public static File zipFiles(final Collection<File> files, final String zipFilePath) {
        if (files == null || files.size() <= 0 || zipFilePath == null) return null;

        byte[] buffer = new byte[BUFFER];
        try {
            LogUtils.tag("feedback").d("zipPath = " + zipFilePath);

            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                if (!zipFile.delete()) {
                    LogUtils.tag("feedback").d("can not delete a exists file : " + zipFilePath);
                    return null;
                }
            }

            if (!zipFile.createNewFile()) {
                LogUtils.tag("feedback").d("can not create a new zip file : " + zipFilePath);
                return null;
            }

            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : files) {
                if (file == null || !file.exists())
                    continue;

                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                FileInputStream in = null;
                try {
                    in = new FileInputStream(file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    LogUtils.tag("feedback").d( "compress logs file not found");
                } finally {
                    IOUtils.close(in);
                }
            }

            zos.closeEntry();
            zos.close();

            return zipFile;
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 解压带有关键字的文件
     *
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     * @return 返回带有关键字的文件链表
     * @throws IOException IO错误时抛出
     */
    public static List<File> unzipFile(final File zipFile, final File destDir)
            throws IOException {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            String entryName = entry.getName();
            String filePath = destDir + File.separator + entryName;
            File file = new File(filePath);
            files.add(file);
            if (entry.isDirectory()) {
                if (!IOUtils.createOrExistsDir(file)) return null;
            } else {
                if (!IOUtils.createOrExistsFile(file)) return null;
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = new BufferedInputStream(zf.getInputStream(entry));
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    byte buffer[] = new byte[BUFFER];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } finally {
                    IOUtils.closeSilent(in);
                    IOUtils.closeSilent(out);
                }
            }
        }
        return files;
    }


    /**
     * 解压缩数据
     *
     * @param data 压缩数据
     * @return 解压缩后的数据
     * @throws IOException IO异常
     */
    public static byte[] ungzip(byte[] data) throws IOException {
        GZIPInputStream zis = new GZIPInputStream(
                new ByteArrayInputStream(data));
        ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[BUFFER];
        while (true) {
            int len = zis.read(buf, 0, buf.length);
            if (len == -1) {
                break;
            }
            os.write(buf, 0, len);
        }
        return os.toByteArray();
    }

    public static byte[] gzip(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        gzip(bais, baos);

        byte[] output = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return output;
    }

    /**
     * gzip 数据压缩
     *
     * @param is 输入流
     * @param os 输出流
     * @throws IOException
     */
    public static void gzip(InputStream is, OutputStream os) throws IOException {
        GZIPOutputStream gos = new GZIPOutputStream(os);

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        gos.flush();
        gos.close();
    }
}
