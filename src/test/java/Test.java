import cn.bosc.monitorcontrol.dao.EntityFetcher;
import cn.bosc.monitorcontrol.entity.List;
import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        EntityFetcher ef = new EntityFetcher();
//        for(List list: ef.getLists()) {
//            System.out.println(list.getMid());
//            System.out.println(list.getName());
//            System.out.println(list.getDescription());
//        }
        for(Rule rule: ef.getRules()) {
            System.out.println(rule.getId());
            System.out.println(rule.getMid());
            System.out.println(rule.getType());
            System.out.println(rule.getSpan());
        }

    }
}
