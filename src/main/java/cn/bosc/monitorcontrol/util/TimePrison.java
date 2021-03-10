package cn.bosc.monitorcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class TimePrison {

    private static final Logger logger = LoggerFactory.getLogger(TimePrison.class);

    public static void trial() throws InterruptedException {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        if (minute != 0 || second != 0) {
            logger.info("Minute = " + minute + ", second = " + second + ", entering time prison...");
        }
        while (true) {
            minute = Calendar.getInstance().get(Calendar.MINUTE);
            if (minute % 10 == 0 && minute != 0 && second == 0) {
                logger.info("Current minute = " + minute + ", second = " + second);
            }
            second = Calendar.getInstance().get(Calendar.SECOND);
            if (minute == 0 && second == 0) {
                break;
            }
            Thread.sleep(1000);
        }
        logger.info("Escaped from time prison.");
    }
}
