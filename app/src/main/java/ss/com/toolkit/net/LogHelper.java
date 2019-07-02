package ss.com.toolkit.net;

import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import ss.com.toolkit.util.ZipUtils;

/**
 * @author Legend on 2014/6/5.
 */
public class LogHelper {

    private static final String TAG = LogHelper.class.getName();

    private static final String LOG_ZIP_FILE_NAME = "logsZip.zip";

    public static File getLogByTime(boolean requireDayLog, String fbId, long mLogBeginTime, long mLogEndTime) {
        try {
            //上一次没传完的，继续上传
            if (!TextUtils.isEmpty(fbId)) {
                String zipPath = getDir() + File.separator + fbId + "_" + LOG_ZIP_FILE_NAME;
                File zipFile = new File(zipPath);
                if (zipFile.exists()) {
                    return zipFile;
                }
            }
            List<File> files = getUploadLogFiles(mLogBeginTime, mLogEndTime);
            String preFix = TextUtils.isEmpty(fbId) ? "" : fbId + "_";
            String zipPath = getDir() + File.separator + preFix + LOG_ZIP_FILE_NAME;

            return ZipUtils.zipFiles(files, zipPath);
        } catch (Exception e) {
            LogUtils.tag("feedback").d("compress logs file error = " + e);
            return null;
        }
    }

    public static List<File> getUploadLogFiles(long logBeginTime, long logEndTime) {
        List<File> fileList = getRenamedXLogFiles();
        if (fileList != null && fileList.size() > 0) {
            List<File> fileListByTime = new ArrayList();
            Iterator var7 = fileList.iterator();

            long fileSize = 0;
            long uploadStartTime = 0;//LogManager.getInstance().getUploadStartTime();
            while (var7.hasNext()) {
                File file = (File) var7.next();
//                if (file.lastModified() >= uploadStartTime/* && fileSize + file.length() <= LogManager.getInstance().getMaxFileSize()*/) {
                    fileListByTime.add(file);
                    fileSize += file.length();
//                }
            }
//            LogManager.getInstance().saveUploadTime(fileListByTime.get(0).lastModified());
            return fileListByTime;
        }
        return null;
    }

    public static List<File> getRenamedXLogFiles() {
        String folderDir = LogManager.LOG_DIR;
        FilenameFilter filenameFilter = (new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("log_") && name.endsWith(".txt");
            }
        });
        return getFilesByDate(folderDir, filenameFilter);
    }

    private static List<File> getFiles(String folderDir, FilenameFilter filenameFilter) {
        File[] files = (new File(folderDir)).listFiles(filenameFilter);
        return files != null ? Arrays.asList(files) : null;
    }

    public static List<File> getFilesByDate(String folderDir, FilenameFilter filenameFilter) {
        File logDir = new File(folderDir);
        Log.i("nadiee", "folderDir ： "+folderDir);
        if (logDir.exists()) {
            Log.i("nadiee", ""+logDir.listFiles().length);
        }
        File[] files = (new File(folderDir)).listFiles(/*filenameFilter*/);
        if (files == null || files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff < 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }
            public boolean equals(Object obj) {
                return true;
            }
        });
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName() + ":" + new Date(files[i].lastModified()));
        }
        return Arrays.asList(files);
    }

    public static boolean isFeedBackLogFileExists(String fbId) {
        if (!TextUtils.isEmpty(fbId)) {
            String zipPath = getDir() + File.separator + fbId + "_" + LOG_ZIP_FILE_NAME;
            File zipFile = new File(zipPath);
            LogUtils.tag("feedback").d("isFeedBackLogFileExists " + zipFile.exists());
            return zipFile.exists();
        }
        return false;
    }

    private static List<File> getUploadLogFiles(boolean requireDayLog, String[] logFileNames) {
        String dir = getDir();
        List<File> files = new ArrayList<File>();
        for (String logFilename : logFileNames) {
            String path = dir + File.separator + logFilename;
            if (!TextUtils.isEmpty(path)) {
                File log = new File(path);
                if (log.exists()) {
                    files.add(log);
                }
            }
        }

        Pattern logPattern = null;
        if (requireDayLog) {
            SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd");
            String pattern = String.format("((logs)|([\\w-]*\\.txt))-%s.*((\\.bak)|(\\.xlog))$",
                    dateformat.format(new Date()));
            logPattern = Pattern.compile(pattern);
        }

        File logDir = new File(dir);
        File[] logFiles = logDir.listFiles();
        if (null != logFiles) {
            for (File dump : logFiles) {
                if (logPattern != null && logPattern.matcher(dump.getName()).matches()) {
                    files.add(dump);
                }
                if (dump.isFile() && dump.getName().contains(".dmp")) {
                    files.add(dump);
                }
            }
        }
        return files;
    }

    private static String getDir() {
        return LogManager.LOG_DIR;
    }

    public static final int DEFAULT_BUFF_SIZE = 64 * 1024;
}
