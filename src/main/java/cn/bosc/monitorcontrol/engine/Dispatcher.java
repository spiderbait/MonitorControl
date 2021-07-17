package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.engine.launcher.CronTaskLauncher;
import cn.bosc.monitorcontrol.engine.launcher.SpanTaskLauncher;
import cn.bosc.monitorcontrol.entity.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

//TODO: create new class by reflect feature
//TODO: judge whether a span task is already exists or not (workaround solved)
public class Dispatcher {

    Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    Parser parser = new Parser();

//    ExecutorService executorService = Executors.newFixedThreadPool(Constant.MAX_THREAD_POOL_SIZE);

    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public void dispatch() {

        try {
            this.parser.parse();
            List<cn.bosc.monitorcontrol.entity.List> lists = parser.getLists();
            HashMap<Integer, List<Rule>> ruleMap = parser.getRuleMap();
            for (cn.bosc.monitorcontrol.entity.List list : lists) {
                logger.debug("Executing scheduled task mid = " + list.getMid() + ", dispatch name = " + list.getName());
                //TODO: add ifnull for list.getMid()
                for (Rule rule : ruleMap.get(list.getMid())) {
                    String name = rule.getName();
                    String type = rule.getType();
                    String span = rule.getSpan();
                    String path = rule.getPath();
                    String endKeyword = rule.getEndKeyword();
                    List<String> jobList = rule.getJobList();
                    String whereClause = rule.getWhereClause();
                    String[] receivers = rule.getReceivers();
                    String title = list.getName() + " - " + name;
                    logger.debug("Executing scheduled task type = " + type + ", time span = " + span
                            + ", output path = " + path + ", where clause = " + whereClause);
                    switch (type.toLowerCase()) {
                        case "cron":
                            executor.execute(new CronTaskLauncher(title, receivers, whereClause, span, path, jobList, endKeyword));
                            logger.info("Submitted an cron task.");
                            break;
                        case "span":
                            executor.execute(new SpanTaskLauncher(title, receivers, whereClause, span, path, jobList, endKeyword));
                            logger.info("Submitted an span task.");
                            break;
                        default:
                            logger.error("TYPE ERROR!");
                            //ERROR
                            break;
                    }
                    logger.debug("Thread number in thread pool: " + executor.getPoolSize() + ", task number waited in queue：" +
                            executor.getQueue().size() + ", completed task count: " + executor.getCompletedTaskCount());
                }
            }
        } catch (NullPointerException e) {
            logger.warn("No rules found for this entity, continue.");

        }
    }
}