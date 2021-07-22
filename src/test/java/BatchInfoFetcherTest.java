import cn.bosc.monitorcontrol.dao.BatchInfoFetcher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchInfoFetcherTest {
    public static void main(String[] args) throws SQLException {
        BatchInfoFetcher bif = new BatchInfoFetcher();
        List<String> list = new ArrayList<>();
        list.add("S04_ACCOUNT");
        list.add("S00_TREE");
        list.add("S00_ORG");
//        list.clear();
        bif.getBatchInfo("WHERE 1=1", list);
//        System.out.println(bif.getDelayedBatch("", list));
    }
}
