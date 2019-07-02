package ss.com.toolkit.retrofit.result;

import io.reactivex.functions.Function;
import ss.com.toolkit.retrofit.exception.ServerException;

public class HttpResultFunc<T> implements Function<T,T>{
    @Override
    public T apply(T httpResult) throws Exception {
        if (httpResult == null) {
            throw new ServerException(100);
        }
        return httpResult;
    }
}
