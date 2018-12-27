package ss.com.toolkit.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
public interface IIpQuery {
    @GET("/ips1388.asp")
    Observable<IpInfo> queryFromIp138();

    @GET(".")
    Observable<IpInfo> queryFromIfconfigMe();
}
