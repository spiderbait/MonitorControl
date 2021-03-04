package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.entity.Rule;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Dispatcher {

    Parser parser = new Parser();

    public void dispatch() {
        this.parser.parse();
        List<cn.bosc.monitorcontrol.entity.List> lists = parser.getLists();
        HashMap<Integer, Rule> ruleMap = parser.getRuleMap();
        Calendar c = Calendar.getInstance();
        int curHour = c.get(Calendar.HOUR_OF_DAY);
        for (cn.bosc.monitorcontrol.entity.List list: lists) {
            String type = ruleMap.get(list.getMid()).getType();
            String span = ruleMap.get(list.getMid()).getSpan();
            switch(type) {
                case "cron":
                    int dispatchHour = Integer.parseInt(span.split(":")[0]);
                    if (curHour == dispatchHour) {
                        // cronLogging
                    }
                    break;
                case "span":
                    int startHour = Integer.parseInt(span.split("->")[0].trim().split(":")[0]);
                    int endHour = Integer.parseInt(span.split("->")[1].trim().split(":")[0]);
                    if (curHour == startHour) {

                    }
                    if (curHour == endHour) {

                    }
                    break;
                default:
                    //ERROR
                    break;
            }
        }

    }

    public static void main(String[] args) {
        Dispatcher d = new Dispatcher();
        d.dispatch();
    }
}
