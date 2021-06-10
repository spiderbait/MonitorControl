package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.dao.BatchInfoFetcher;
import cn.bosc.monitorcontrol.util.LoggingFileCleanUtil;
import cn.bosc.monitorcontrol.util.StringUtil;
import cn.bosc.monitorcontrol.util.TimestampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Calendar;
import java.util.List;

public class MonitorLogger {
    // SpanLogger
    // CronLogger
    BatchInfoFetcher bif = new BatchInfoFetcher();
    Logger logger = LoggerFactory.getLogger(MonitorLogger.class);

    public void spanLogging(String whereClause, String span, String path, List<String> jobList, String endKeyword) {
        try {

            int startHour = Integer.parseInt(span.split("->")[0].trim().split(":")[0]);
            int endHour = Integer.parseInt(span.split("->")[1].trim().split(":")[0]);
            String truePath = path.replace("yyyymmdd",
                    TimestampUtil.getNowTimestampString(Constant.PATH_FILENAME_FORMAT));
            LoggingFileCleanUtil.clean(truePath);
            logger.debug("Span logging: current hour = " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                    + ", start hour = " + startHour
                    + ", end hour = " + endHour
                    + ", end keyword = " + endKeyword);
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == startHour) {
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(truePath, true)));
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " START\n");
                while (true) {
                    out.write(bif.getBatchInfo(whereClause, jobList));
                    out.flush();
                    Thread.sleep(Constant.SPAN_DELAY_IN_MS);
                    if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == endHour) {
                        out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " " + endKeyword + "\n");
                        out.close();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cronLogging(String whereClause, String span, String path, List<String> jobList, String endKeyword) {
        try {
            int startHour = Integer.parseInt(span.split(":")[0]);
            logger.debug("Cron logging: current hour = " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                    + ", start hour = " + startHour
                    + ", end keyword = " + endKeyword);
            if (startHour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) { // Compare current hour and dispatch hour, start logging if match
                String truePath = path.replace("yyyymmdd",
                        TimestampUtil.getNowTimestampString(Constant.PATH_FILENAME_FORMAT));
                LoggingFileCleanUtil.clean(truePath);
                BufferedWriter out = new BufferedWriter(new FileWriter(truePath));
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " START\n");
                String msg = bif.getBatchInfo(whereClause, jobList);
                out.write(msg);
                out.write(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT) + " " + endKeyword + "\n");
                String probe = StringUtil.getProbeQueryString(jobList);
                out.write(probe);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
