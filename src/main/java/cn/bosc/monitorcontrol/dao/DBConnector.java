package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.util.PropertiesUtil;
import jdk.nashorn.internal.scripts.JD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBConnector {

    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASS;
    Connection conn = null;
    Statement stmt = null;
    Logger logger = LoggerFactory.getLogger(DBConnector.class);

    public void loadMySQLParameters() {
        this.JDBC_DRIVER = PropertiesUtil.getProperty("sys.config.mysql.driver");
        this.DB_URL = PropertiesUtil.getProperty("sys.config.mysql.url");
        this.USER = PropertiesUtil.getProperty("sys.config.mysql.user");
        this.PASS = PropertiesUtil.getProperty("sys.config.mysql.password");
        logger.info("MySQL parameters loaded.");
    }

    public void loadOracleParameters() {
        this.JDBC_DRIVER = PropertiesUtil.getProperty("sys.config.oracle.driver");
        this.DB_URL = PropertiesUtil.getProperty("sys.config.oracle.url");
        this.USER = PropertiesUtil.getProperty("sys.config.oracle.user");
        this.PASS = PropertiesUtil.getProperty("sys.config.oracle.password");
        logger.info("Oracle parameters loaded.");
    }

    public void getConnection(String type) {
        switch(type) {
            case "MySQL":
                loadMySQLParameters();break;
            case "Oracle":
                loadOracleParameters();break;
        }
        try {
            Class.forName(JDBC_DRIVER);
            logger.info("JDBC DRIVER: " + JDBC_DRIVER);
            // 打开链接
            logger.info("连接" + type + "数据库...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            logger.info("实例化" + type + "对象...");
            this.stmt = conn.createStatement();
        } catch(Exception e){
            // 处理 JDBC 错误
            e.printStackTrace();
        }// 处理 Class.forName 错误

//        }finally{
//            // 关闭资源
//            try{
//                if(stmt!=null) stmt.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }// 什么都不做
//            try{
//                if(conn!=null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
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

    public void closeConnection() {
        try{
            if(stmt!=null) stmt.close();
            if(conn!=null) conn.close();
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DBConnector dbc = new DBConnector();
        dbc.getConnection("Oracle");
        ResultSet rs = dbc.execQuery("select * from etl_job");
        while(rs.next()) {
            System.out.println(rs.getString(1));
        }
        dbc.closeConnection();
    }

}
