package cn.bosc.monitorcontrol.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MonitorLogger {
    // SpanLogger
    // CronLogger

    public void spanLogging(String span, String path) {

    }

    public void cronLogging(String path) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write("just gogogo");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MonitorLogger monitorLogger = new MonitorLogger();
        String path = "/Users/tianzhuoli/Desktop/dev/tmp/monitor_20210304.log";
        monitorLogger.cronLogging(path);
    }

}
