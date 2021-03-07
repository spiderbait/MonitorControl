import cn.bosc.monitorcontrol.dao.EntityFetcher;
import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.SQLException;

public class EntityFetcherTest {
    public static void main(String[] args) throws SQLException {
        EntityFetcher ef = new EntityFetcher();
        for(Rule r: ef.getRulesByMid(1)) {
            System.out.println(r.getJobList());
        }
    }
}
