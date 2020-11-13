package uz.smartgeo.lifemap.api.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Saidolim on 28.05.15.
 */
public class Converters {

    /**
     * Converts String type float value into Float.
     * Before convert, replaces all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return float value of result
     */
    public static Float strToFloat(String value, Float defaultValue) {
        try {
            String valueWithDot = value.replaceAll(",", ".");
            valueWithDot = valueWithDot.replaceAll(" ", "");
            return Float.valueOf(valueWithDot);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type double value into Double.
     * Before convert, replaces all commas (,) to dots (.)
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return double value of result
     */
    public static Double strToDouble(String value, Double defaultValue) {
        try {
            String valueWithDot = value.replaceAll(",", ".");
            valueWithDot = valueWithDot.replaceAll(" ", "");
            Double f = Double.valueOf(valueWithDot);
            return f;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type int value into Integer.
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return float value of result
     */
    public static Integer strToInt(String value, Integer defaultValue) {
        try {
            String valueWithDot = value.replaceAll(" ", "");
            Integer v = Integer.valueOf(valueWithDot);
            return v;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * Converts String type int value into Long.
     *
     * @param value        value to convert
     * @param defaultValue default value if there is some error
     * @return Long value of result
     */
    public static Long strToLong(String value, Long defaultValue) {
        try {
            String valueWithDot = value.replaceAll(" ", "");
            return Long.valueOf(valueWithDot);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * @param in
     * @param position
     * @return
     */
    public static int getBitValue(int in, int position) {
        return (in >> position) & 1;
    }

    /**
     * @param in
     * @param position
     * @param value
     * @return
     */
    public static int setBitValue(int in, int position, int value) {
        if (position > 0 && position < 31) {
            if (value == 0) {
                return in & (0xBFFFFFFD >> (30 - position));
            } else {
                return in | (1 << (position));
            }
        }
        return in;
    }

    /**
     * Timestamp to String format "dd.MM.yyyy HH:mm"
     *
     * @param time Timestamp object
     * @return "" if null
     */
    public static String timeToStr(Timestamp time) {
        if (time != null) {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(time);
        }
        return "";
    }


    /**
     * Reads params from query, each name must start with : symbol, List keeps exact order.
     * <p>
     * Example : "INSERT INTO auto.auto(mobject_id, auto_name) VALUES (:mobject_id, :auto_name);"
     * <p>
     * Result : [mobject_id, auto_name]
     *
     * @param query       SQL string with params
     * @param queryParams List to store the params.
     */
    public static void readQueryNamedParams(String query, List<String> queryParams) {
        Pattern p = Pattern.compile("(?:(:[a-zA-Z0-9_]+))([^:]+)(?=\\s|$)");
        Matcher m = p.matcher(query);

        while (m.find()) {
            if (m.group(1) != null) queryParams.add(m.group(1).substring(1));
        }
    }

    public static String replaceParams(String what) {
        return (what != null && !what.isEmpty())
                ? what.replaceAll(":[a-zA-Z0-9_]+", "?")
                : what;
    }

}
