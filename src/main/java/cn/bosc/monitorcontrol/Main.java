package cn.bosc.monitorcontrol;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.engine.Dispatcher;
import cn.bosc.monitorcontrol.util.TimePrison;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("MonitorControl starting...");
        try {
            TimePrison.trial();
            while (true) {
                dispatcher.dispatch();
                logger.info("One turn executed.");
                Thread.sleep(Constant.DISPATCH_INTERVAL_IN_MS);
            }
        } catch (InterruptedException e) {
            System.out.println("Bye!");
        }
    }
}
