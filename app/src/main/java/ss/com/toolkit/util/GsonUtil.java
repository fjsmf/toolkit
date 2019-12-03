package ss.com.toolkit.util;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public class GsonUtil {
    // 可以根据需要，将serializeNulls设置成true,serializeNull值的区别：
    // https://futurestud.io/tutorials/gson-builder-force-serialization-of-null-values
    private static final Gson GSON = createGson(false);

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Class<T> type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Type type) {
        return GSON.fromJson(reader, type);
    }

    public static String toJson(Object o) {
        try {
            return GSON.toJson(o);
        } catch (Exception e) {
            LogUtils.e("GsonUtil toJson met an error object: " + o);
            return null;
        }
    }

    public static void toJson(Writer writer, Object o, Type type) {
        try {
            JsonWriter w = new JsonWriter(writer);
            GSON.toJson(o, type, w);
            w.flush();
            w.close();
        } catch (Exception e) {
            LogUtils.e("GsonUtil toJson met an error object:" + o);
        }
    }

    /**
     * Create a pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    private static Gson createGson(final boolean serializeNulls) {
        final GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder.create();
    }

    /**
     * 判断一个字符串是不是有效的json
     */
    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            JsonElement jsonElement = new JsonParser().parse(json);
            return jsonElement.isJsonObject();
        } catch (JsonParseException e) {
            return false;
        }
    }
}
