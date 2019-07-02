package ss.com.toolkit.net;

/**
 * @author dh on 2016/5/12.
 */
public class LogAutoAnalyzeConstants {

    private static final String SERVER_URL_GM = "https://ffilelogapp.nimo.tv";
    //反馈问题
    public static final String FEEDBACK_URL = SERVER_URL_GM + "/addFeedBack";

    //添加问题
    public static final String KEY_FB_TYPE = "fbType";
    public static final String KEY_FB_DETAILS = "fbDetails";
    public static final String KEY_FB_UID = "uid";
    public static final String KEY_FB_GID = "gid";
    public static final String KEY_FB_LOGNAMELIST = "logNameList";
    public static final String KEY_FB_APPVERSION = "appVersion";
    public static final String KEY_FB_APPVERSION_CODE = "appVersionCode";
    public static final String KEY_FB_DEVICETYPE = "deviceType";//2 Android phone, 3 Android PAD
    public static final String KEY_FB_APPID = "appid";//业务类型：200 虎牙直播业务
    public static final String VALUE_FB_APPID = "6000"; // 小游戏日志上传 appid
    public static final String KEY_FB_FILETYPE = "fileType";//文件的类型，取值为log或dump，不填默认是log
    public static final String KEY_FB_ANCHORID = "anchorId";//主播id，作为补充信息
    public static final String KEY_FB_SSID = "ssid";//子频道id，作为补充信息
    public static final String KEY_FB_ISP2P = "isp2p";


    //上传Log文件
    private static final String SERVER_UPLOAD_ANALYZE_URL = "https://ffilelogupload-an.nimo.tv";
    public static final String LOG_UPLOAD_URL = SERVER_UPLOAD_ANALYZE_URL + "/uploadLog";
    public static final String KEY_LOG_FBID = "fbId";
    public static final String KEY_LOG_ISRELOAD = "isReload";
    public static final String KEY_LOG_MD5 = "md5";
    public static final String KEY_LOG_FILESIZE = "fileSize";
    public static final String KEY_LOG_BEGIN_POSITION = "beginPos";

    //根据反馈ID查找服务器上文件的上传状态
    public static final String LOG_GET_FILE_RANGE_URL = SERVER_UPLOAD_ANALYZE_URL + "/getRemoteFileRange";

    //用户重新上线拉取是否需要上传日志
    public static final String QUERY_IS_NEED_UPLOAD_LOG = SERVER_URL_GM + "/isNeedUploadLog";

    //根据反馈ID和用户ID补充必要的数据
    public static final String LOG_ADD_DEVICE_DETAILS = SERVER_URL_GM + "/addDeviceDetails";
    public static final String KEY_LOG_DEVICE = "device";

}
