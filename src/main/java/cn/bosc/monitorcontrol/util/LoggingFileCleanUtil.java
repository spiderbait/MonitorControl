package cn.bosc.monitorcontrol.util;

import java.io.File;

public class LoggingFileCleanUtil {
    public static void clean(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("历史日志文件存在，已删除该路径文件：" + path);
            }
        } else {
            System.out.println("不存在历史日志文件，将会创建新的日志文件。");
        }
    }
}
