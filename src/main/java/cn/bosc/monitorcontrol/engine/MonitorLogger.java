package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.dao.BatchInfoFetcher;
import cn.bosc.monitorcontrol.util.LoggingFileCleanUtil;
import cn.bosc.monitorcontrol.util.TimestampUtil;

import java.io.*;
import java.util.Calendar;
import java.util.List;

public class MonitorLogger {
    // SpanLogger
    // CronLogger
    BatchInfoFetcher bif = new BatchInfoFetcher();

    public void spanLogging(String whereClause, String span, String path, List<String> jobList) {
        try {

            int startHour = Integer.parseInt(span.split("->")[0].trim().split(":")[0]);
            int endHour = Integer.parseInt(span.split("->")[1].trim().split(":")[0]);
            String truePath = path.replace("yyyymmdd",
                    TimestampUtil.getNowTimestampString(Constant.PATH_FILENAME_FORMAT));
            LoggingFileCleanUtil.clean(truePath);
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(truePath, true)));
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == startHour) {
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " START\n");
                while (true) {
                    out.write(bif.getBatchInfo(whereClause, jobList));
                    out.flush();
                    Thread.sleep(Constant.SPAN_DELAY_IN_MS);
                    if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == endHour) {
                        out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " END\n");
                        out.close();
                        System.exit(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cronLogging(String whereClause, String span, String path, List<String> jobList) {
        try {
            if (Integer.parseInt(span.split(":")[0]) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) { // Compare current hour and dispatch hour, start logging if match
                String truePath = path.replace("yyyymmdd",
                        TimestampUtil.getNowTimestampString(Constant.PATH_FILENAME_FORMAT));
                LoggingFileCleanUtil.clean(truePath);
                BufferedWriter out = new BufferedWriter(new FileWriter(truePath));
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " START\n");
                String msg = bif.getBatchInfo(whereClause, jobList);
                out.write(msg);
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " END\n");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
