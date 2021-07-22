package cn.bosc.monitorcontrol.engine.launcher;

import cn.bosc.monitorcontrol.engine.MonitorLogger;

import java.util.List;

public class CronTaskLauncher implements Runnable{

    String span;
    String path;
    List<String> jobList;
    String whereClause;
    String endKeyword;
    MonitorLogger ml;
    String title;
    String[] receivers;

    public CronTaskLauncher(String title, String[] receivers, String whereClause, String span, String path, List<String> jobList, String endKeyword, MonitorLogger ml) {
        this.whereClause = whereClause;
        this.span = span;
        this.path = path;
        this.jobList = jobList;
        this.endKeyword = endKeyword;
        this.title = title;
        this.receivers = receivers;
        this.ml = ml;
    }

    @Override
    public void run() {
        ml.cronLogging(this.title, this.receivers, this.whereClause, this.span, this.path, this.jobList, this.endKeyword);
    }
}
