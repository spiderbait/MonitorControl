package cn.bosc.monitorcontrol.util;

import java.util.List;

public class StringUtil {

    public static String jobNameJoin(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String delimiter = ",";
        int i = 0;
        for (String s: list) {
            if (i == list.size() - 1) {
                sb.append("'").append(s).append("'");
            } else {
                s = s.replace("'", "");
                sb.append("'").append(s).append("'").append(delimiter).append("\n");
            }
            i ++;
        }
        return sb.toString();
    }


}
