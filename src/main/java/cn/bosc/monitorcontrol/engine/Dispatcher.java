package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.entity.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: create new class by reflect feature
//TODO: judge whether a span task is already exists or not
public class Dispatcher {

    Parser parser = new Parser();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void dispatch() {
        this.parser.parse();
        List<cn.bosc.monitorcontrol.entity.List> lists = parser.getLists();
        HashMap<Integer, Rule> ruleMap = parser.getRuleMap();
        for (cn.bosc.monitorcontrol.entity.List list: lists) {
            String type = ruleMap.get(list.getMid()).getType();
            String span = ruleMap.get(list.getMid()).getSpan();
            String path = ruleMap.get(list.getMid()).getPath();
            List<String> jobList = ruleMap.get(list.getMid()).getJobList();
            System.out.println("执行调度作业类型：" + type + "，持续时间：" + span + "输出路径" + path);
            switch(type.toLowerCase()) {
                case "cron":
                    executorService.execute(new CronTaskLauncher(span, path, jobList));
                    break;
                case "span":
                    System.out.println();
                    executorService.execute(new SpanTaskLauncher(span, path, jobList));
                    break;
                default:
                    //ERROR
                    break;
            }
        }
    }
}

class CronTaskLauncher implements Runnable{

    String span;
    String path;
    List<String> jobList;
    MonitorLogger ml = new MonitorLogger();

    public CronTaskLauncher(String span, String path, List<String> jobList) {
        this.span = span;
        this.path = path;
        this.jobList = jobList;
    }

    @Override
    public void run() {
        ml.cronLogging(this.span, this.path, this.jobList);
    }
}

class SpanTaskLauncher implements Runnable{

    String span;
    String path;
    List<String> jobList;
    MonitorLogger ml = new MonitorLogger();

    public SpanTaskLauncher(String span, String path, List<String> jobList) {
        this.span = span;
        this.path = path;
        this.jobList = jobList;
    }

    @Override
    public void run() {
        ml.spanLogging(this.span, this.path, this.jobList);
    }
}