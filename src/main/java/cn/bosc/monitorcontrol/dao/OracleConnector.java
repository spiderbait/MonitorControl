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

//    public OracleConnector(){
//        try {
//            Class.forName(JDBC_DRIVER);
//
//            // 打开链接
//            logger.info("Connecting Oracle database...");
//            logger.debug("DB_URL = " + DB_URL);
//            logger.debug("USER = " + USER);
//            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            // 执行查询
//            logger.info("Instantiating Oracle object...");
//            this.stmt = conn.createStatement();
//        } catch(Exception e){
//            // 处理 JDBC 错误
//            e.printStackTrace();
//        }// 处理 Class.forName 错误
//
//    }

    public boolean getConnection() {
        if (this.conn != null || this.stmt != null) {
            return false;
        }
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            logger.info("Connecting Oracle database...");
            logger.debug("DB_URL = " + DB_URL);
            logger.debug("USER = " + USER);
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            // 执行查询
            logger.info("Instantiating Oracle object...");
            this.stmt = conn.createStatement();
            logger.info("Oracle connection established.");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet rs = null;
        try {
//            Class.forName(JDBC_DRIVER);

            logger.info("Connecting Oracle database...");
            logger.debug("DB_URL = " + DB_URL);
            logger.debug("USER = " + USER);
            logger.debug("Fetched result for query: " + query);
//            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            this.stmt = this.conn.createStatement();
            rs = this.stmt.executeQuery(query);
//            if (this.stmt != null) {
//                this.stmt.close();
//            }
//            if (this.conn != null) {
//                this.conn.close();
//            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void closeConnection() throws SQLException {
        if (this.stmt != null) {
            this.stmt.close();
        }
        if (this.conn != null) {
            this.conn.close();
        }
        logger.debug("Oracle connection closed.");
    }

    public static void main(String[] args) throws SQLException{

        OracleConnector oc = new OracleConnector();
        oc.getConnection();
        ResultSet rs = oc.execQuery("select * from etl_job");
        while(rs.next()) {
            System.out.println(rs.getString("etl_job"));
        }
        oc.closeConnection();

    }
}
