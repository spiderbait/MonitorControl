package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

//TODO: merge oracle and mysql into one class
public class OracleConnector {
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = PropertiesUtil.getProperty("sys.config.oracle.url");
    static final String USER = PropertiesUtil.getProperty("sys.config.oracle.user");
    static final String PASS = PropertiesUtil.getProperty("sys.config.oracle.password");
    Connection conn = null;
    Statement stmt = null;
    Logger logger = LoggerFactory.getLogger(OracleConnector.class);

    public OracleConnector(){
        try {
            Class.forName(JDBC_DRIVER);

            // 打开链接
            logger.info("Connecting Oracle database...");
            logger.debug("DB_URL = " + DB_URL);
            logger.debug("USER = " + USER);
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            logger.info("Instantiating Oracle object...");
            this.stmt = conn.createStatement();
        } catch(Exception e){
            // 处理 JDBC 错误
            e.printStackTrace();
        }// 处理 Class.forName 错误

    }

    public ResultSet execQuery(String query) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return rs;
    }

}
