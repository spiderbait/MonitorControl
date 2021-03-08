package cn.bosc.monitorcontrol.engine.launcher;

import cn.bosc.monitorcontrol.engine.MonitorLogger;

import java.util.List;

public class CronTaskLauncher implements Runnable{

    String span;
    String path;
    List<String> jobList;
    String whereClause;
    MonitorLogger ml = new MonitorLogger();

    public CronTaskLauncher(String whereClause, String span, String path, List<String> jobList) {
        this.whereClause = whereClause;
        this.span = span;
        this.path = path;
        this.jobList = jobList;
    }

    @Override
    public void run() {
        ml.cronLogging(this.whereClause, this.span, this.path, this.jobList);
    }
}
