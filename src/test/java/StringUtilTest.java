import cn.bosc.monitorcontrol.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class StringUtilTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("S04_ACCOUNT");
        list.add("S00_TREE");
        System.out.println(StringUtil.jobNameJoin(list));
    }
}
