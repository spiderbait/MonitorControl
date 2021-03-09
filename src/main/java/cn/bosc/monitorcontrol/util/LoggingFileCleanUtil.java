package cn.bosc.monitorcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggingFileCleanUtil {
    static final Logger logger = LoggerFactory.getLogger(LoggingFileCleanUtil.class);
    public static void clean(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                logger.info("Old logging file exists, creating new at " + path);
            }
        } else {
            logger.info("No old logging file exists, creating new at " + path);
        }
    }
}
