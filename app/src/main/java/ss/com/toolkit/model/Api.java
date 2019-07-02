package ss.com.toolkit.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ss.com.toolkit.model.Response.AddFeedBackRsp;
import ss.com.toolkit.model.Response.IsNeedUploadLogRsp;
import ss.com.toolkit.model.Response.LogUploadRangeRsp;

public interface Api {
    @POST("/addFeedBack")
    @FormUrlEncoded
    Call<AddFeedBackRsp> getAdInfo(@Field("fbType") String fbType,
                                   @Field("fbDetails") String fbDetails,
                                   @Field("uid") String uid,
                                   @Field("deviceType") String deviceType,
                                   @Field("appid") String appid);

    @POST("/isNeedUploadLog")
    @FormUrlEncoded
    Call<IsNeedUploadLogRsp> isNeedUploadLog(@Field("appVersion") String appVersion,
                                             @Field("uid") String uid,
                                             @Field("deviceType") String deviceType,
                                             @Field("appid") String appid);

    @POST("/getRemoteFileRange")
    @FormUrlEncoded
    Call<LogUploadRangeRsp> getRemoteFileRange(@Field("fbId") String fbId);

    @POST("/uploadLog")
    @FormUrlEncoded
    Call<IsNeedUploadLogRsp> uploadLog(@Field("fbId") String fbId,
                                       @Field("isReload") String isReload,
                                       @Field("md5") String md5,
                                       @Field("fileSize") String fileSize,
                                       @Field("beginPos") String beginPos);

    @Multipart
    @POST("/uploadLog")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

//    @Multipart
    @POST("/uploadLog")
    Call<ResponseBody> upload(@Body RequestBody body);
}
