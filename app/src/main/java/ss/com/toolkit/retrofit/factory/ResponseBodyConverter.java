package ss.com.toolkit.retrofit.factory;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "ResponseBodyConverter";
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * @param responseBody
     * @return T
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
       String response = responseBody.string();

        // 对字节数组进行解密操作
        String decryptString = response;//EncryptionUtil.decrypt(response);
        LogUtils.tag(TAG).v(decryptString);
        // 这部分代码参考官方GsonConverterFactory中GsonResponseBodyConverter<T>的源码对json的处理
        Reader reader = StringToReader(decryptString);

        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            reader.close();
            jsonReader.close();
        }
    }

    private Reader StringToReader(String json) {
        Reader reader = new StringReader(json);
        return reader;
    }
}
