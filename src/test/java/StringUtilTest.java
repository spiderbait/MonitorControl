import cn.bosc.monitorcontrol.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class StringUtilTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String whereClause = "where last_txdate>=sys_date()-1";
        list.add("S04_ACCOUNT");
        list.add("S00_TREE");
        System.out.println(StringUtil.jobNameJoin(list));
        System.out.println(StringUtil.getQueryString(whereClause, list));
        System.out.println(StringUtil.getProbeQueryString(list));
    }
}
