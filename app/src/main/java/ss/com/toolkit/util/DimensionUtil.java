package ss.com.toolkit.util;

public class DimensionUtil {
    public static float getDistance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(Math.pow(Math.abs(x1 - x2), 2)
                +Math.pow(Math.abs(y1 - y2), 2));
    }
}
