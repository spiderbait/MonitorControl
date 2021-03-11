package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.engine.launcher.CronTaskLauncher;
import cn.bosc.monitorcontrol.engine.launcher.SpanTaskLauncher;
import cn.bosc.monitorcontrol.entity.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: create new class by reflect feature
//TODO: judge whether a span task is already exists or not (workaround solved)
public class Dispatcher {

    Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    Parser parser = new Parser();
    ExecutorService executorService = Executors.newFixedThreadPool(Constant.MAX_THREAD_POOL_SIZE);

    public void dispatch() {
        this.parser.parse();
        List<cn.bosc.monitorcontrol.entity.List> lists = parser.getLists();
        HashMap<Integer, List<Rule>> ruleMap = parser.getRuleMap();
        for (cn.bosc.monitorcontrol.entity.List list: lists) {
                logger.debug("Executing scheduled task mid = " + list.getMid() + ", dispatch name = " + list.getName());
                for (Rule rule: ruleMap.get(list.getMid())) {
                    String type = rule.getType();
                    String span = rule.getSpan();
                    String path = rule.getPath();
                    List<String> jobList = rule.getJobList();
                    String whereClause = rule.getWhereClause();
                    logger.debug("Executing scheduled task type = " + type + ", time span = " + span
                            + ", output path = " + path + ", where clause = " + whereClause);
                    switch(type.toLowerCase()) {
                        case "cron":
                            executorService.execute(new CronTaskLauncher(whereClause, span, path, jobList));
                            logger.info("Submitted an cron task.");
                            break;
                        case "span":
                            executorService.execute(new SpanTaskLauncher(whereClause, span, path, jobList));
                            logger.info("Submitted an span task.");
                            break;
                        default:
                            logger.error("TYPE ERROR!");
                            //ERROR
                            break;
                }
            }
        }
    }
}