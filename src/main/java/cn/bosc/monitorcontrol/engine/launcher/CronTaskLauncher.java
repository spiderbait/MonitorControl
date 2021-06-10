package cn.bosc.monitorcontrol.engine.launcher;

import cn.bosc.monitorcontrol.engine.MonitorLogger;

import java.util.List;

public class CronTaskLauncher implements Runnable{

    String span;
    String path;
    List<String> jobList;
    String whereClause;
    String endKeyword;
    MonitorLogger ml = new MonitorLogger();

    public CronTaskLauncher(String whereClause, String span, String path, List<String> jobList, String endKeyword) {
        this.whereClause = whereClause;
        this.span = span;
        this.path = path;
        this.jobList = jobList;
        this.endKeyword = endKeyword;
    }

    @Override
    public void run() {
        ml.cronLogging(this.whereClause, this.span, this.path, this.jobList, this.endKeyword);
    }
}
