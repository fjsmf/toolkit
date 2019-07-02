package ss.com.toolkit.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by legend on 15/5/20.
 * <p>
 * modified by mylhyz
 */
public final class IOUtils {
    private IOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String TAG = "IOUtils";

    // return null if no exist or is not dir
    public static File getDir(File rootDir, String... dirs) {
        File currentDir = rootDir;
        for (String dir : dirs) {
            currentDir = new File(currentDir, dir);
            if (!currentDir.exists() || !currentDir.isDirectory()) {
                return null;
            }
        }
        return currentDir;
    }

    // return null if no exist or is dir
    public static File getFile(File rootDir, String fileName, String... dirs) {
        File parentDir = getDir(rootDir, dirs);
        if (parentDir == null) {
            return null;
        }
        File file = new File(parentDir, fileName);
        return file.exists() && !file.isDirectory() ? file : null;
    }

    public static File createDirIfNoExist(File rootDir, String... dirs) {
        File currentDir = rootDir;
        for (String dir : dirs) {
            currentDir = new File(currentDir, dir);
            if ((currentDir.exists() && !currentDir.isDirectory())
                    || (!currentDir.exists() && !currentDir.mkdir())) {
                return null;
            }
        }
        return currentDir;
    }

    public static File createFileIfNoExist(File rootDir, String fileName, String... dirs) {
        File parentDir = createDirIfNoExist(rootDir, dirs);
        if (parentDir == null) {
            return null;
        }
        File file = new File(parentDir, fileName);
        if (file.isDirectory()) {
            return null;
        }
        try {
            return file.createNewFile() ? file : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getFileSize(File file) {
        if (null == file) {
            return 0;
        }
        if (file.isDirectory()) {
            File fileList[] = file.listFiles();
            if (fileList == null) {
                return 0;
            }
            long size = 0;
            for (File item : fileList) {
                size += getFileSize(item);
            }
            return size;
        }
        return file.length();
    }

    public static boolean removeFile(File file, boolean deleteOwn) {
        if (file == null) {
            return false;
        }
        boolean success = true;
        if (file.isDirectory()) {
            File fileList[] = file.listFiles();
            if (fileList != null) {
                for (File item : fileList) {
                    success &= removeFile(item, true);
                }
            }
        }
        return success && (!deleteOwn || file.delete());
    }

    public static byte[] readBytes(File rootDir, String fileName, String... dirs) {
        return readBytes(getFile(rootDir, fileName, dirs));
    }

    public static String readString(File file) {
        return new String(readBytes(file));
    }

    public static byte[] readBytes(File file) {
        if (file == null) {
            return new byte[0];
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            byte[] data = outputStream.toByteArray();
            outputStream.close();
            inputStream.close();
            return data;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static boolean writeBytes(File rootDir, String fileName, byte[] data
            , String... dirs) {
        return writeBytes(createFileIfNoExist(rootDir, fileName, dirs), data);
    }

    public static boolean writeBytes(File file, byte[] data) {
        if (file == null) {
            return false;
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static void close(final Closeable... closeables) {
        if (null == closeables) {
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                if (null != closeable) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeSilent(final Closeable... closeables) {
        if (null == closeables) {
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                if (null != closeable) {
                    closeable.close();
                }
            } catch (IOException e) {
                //ignored
            }
        }
    }
}

