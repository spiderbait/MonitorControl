package cn.bosc.monitorcontrol.engine.launcher;

import cn.bosc.monitorcontrol.engine.MonitorLogger;

import java.util.List;

public class SpanTaskLauncher implements Runnable{

    String title;
    String[] receivers;
    String span;
    String path;
    List<String> jobList;
    String whereClause;
    String endKeyword;
    MonitorLogger ml;

    public SpanTaskLauncher(String title, String[] receivers, String whereClause, String span, String path, List<String> jobList, String endKeyword, MonitorLogger ml) {
        this.title = title;
        this.receivers = receivers;
        this.whereClause = whereClause;
        this.span = span;
        this.path = path;
        this.jobList = jobList;
        this.endKeyword = endKeyword;
        this.ml = ml;
    }

    @Override
    public void run() {
        ml.spanLogging(this.title, this.receivers, this.whereClause, this.span, this.path, this.jobList, this.endKeyword);
    }
}
