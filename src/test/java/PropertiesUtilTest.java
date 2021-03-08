import cn.bosc.monitorcontrol.util.PropertiesUtil;

public class PropertiesUtilTest {
    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getProperty("sys.config.db.mysql.url"));
    }
}
