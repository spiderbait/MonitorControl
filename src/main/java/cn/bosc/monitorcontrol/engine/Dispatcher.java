package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.engine.launcher.CronTaskLauncher;
import cn.bosc.monitorcontrol.engine.launcher.SpanTaskLauncher;
import cn.bosc.monitorcontrol.entity.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: create new class by reflect feature
//TODO: judge whether a span task is already exists or not (workaround solved)
public class Dispatcher {

    Parser parser = new Parser();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void dispatch() {
        this.parser.parse();
        List<cn.bosc.monitorcontrol.entity.List> lists = parser.getLists();
        HashMap<Integer, List<Rule>> ruleMap = parser.getRuleMap();
        for (cn.bosc.monitorcontrol.entity.List list: lists) {
                System.out.println("list out -> " + list.getName());
                for (Rule rule: ruleMap.get(list.getMid())) {
                    String type = rule.getType();
                    String span = rule.getSpan();
                    String path = rule.getPath();
                    List<String> jobList = rule.getJobList();
                    String whereClause = rule.getWhereClause();
                    System.out.println("执行调度作业类型：" + type + "，持续时间：" + span + "，输出路径：" + path);
                    switch(type.toLowerCase()) {
                        case "cron":
                            System.out.println("提交了一个cron任务。");
                            executorService.execute(new CronTaskLauncher(whereClause, span, path, jobList));
                            break;
                        case "span":
                            System.out.println("提交了一个span任务。");
                            executorService.execute(new SpanTaskLauncher(whereClause, span, path, jobList));
                            break;
                        default:
                            //ERROR
                            break;
                }
            }
        }
        executorService.shutdown();
    }
}