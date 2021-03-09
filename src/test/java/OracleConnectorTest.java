import cn.bosc.monitorcontrol.dao.OracleConnector;
import cn.bosc.monitorcontrol.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleConnectorTest {
    public static void main(String[] args) throws SQLException {
        OracleConnector oc = new OracleConnector();
        List<String> list = new ArrayList<>();
        list.add("S04_ACCOUNT");
        list.add("S00_TREE");
        ResultSet rs = oc.execQuery(StringUtil.getQueryString("WHERE COUNT", list));
        int i = 0;
        while (rs.next()) {
            System.out.println(i);
            int s = rs.getInt("COUNT(*)");
            System.out.println(s);
            i ++;
        }
        System.out.println();
    }
}
