package shared.expenses.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static float round(float value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static String getKeyFromValue(HashMap<String, Float> hm, Float value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o.toString();
            }
        }
        return null;
    }

    public static HashMap<String, Float> clone(Map<String, Float> original) {
        return new HashMap<>(original);
    }

}
