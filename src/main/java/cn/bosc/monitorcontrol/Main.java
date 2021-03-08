package cn.bosc.monitorcontrol;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.engine.Dispatcher;
import cn.bosc.monitorcontrol.util.TimestampUtil;

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        try {
//            while (true) {
                dispatcher.dispatch();
                System.out.println(TimestampUtil.getNowTimestampString("yyyy-MM-dd HH:mm:ss") + " 本轮执行结束。");
                Thread.sleep(1000);

//            }
        } catch (InterruptedException e) {
            System.out.println("Bye!");
        }
    }
}
