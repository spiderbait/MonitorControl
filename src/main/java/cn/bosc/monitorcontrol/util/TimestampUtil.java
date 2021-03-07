package cn.bosc.monitorcontrol.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
    public static String getNowTimestampString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

}
