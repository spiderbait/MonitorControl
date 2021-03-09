package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBConnector {

    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = PropertiesUtil.getProperty("sys.config.mysql.url");
    private String USER = PropertiesUtil.getProperty("sys.config.mysql.user");
    private String PASS = PropertiesUtil.getProperty("sys.config.mysql.password");
    Connection conn = null;
    Statement stmt = null;
    Logger logger = LoggerFactory.getLogger(DBConnector.class);

    public void loadMySQLParameters() {
        this.JDBC_DRIVER = PropertiesUtil.getProperty("sys.config.mysql.driver");
        this.DB_URL = PropertiesUtil.getProperty("sys.config.mysql.url");
        this.USER = PropertiesUtil.getProperty("sys.config.mysql.user");
        this.PASS = PropertiesUtil.getProperty("sys.config.mysql.password");
    }

    public void loadOracleParameters() {
        this.JDBC_DRIVER = PropertiesUtil.getProperty("sys.config.oracle.driver");
        this.DB_URL = PropertiesUtil.getProperty("sys.config.oracle.url");
        this.USER = PropertiesUtil.getProperty("sys.config.oracle.user");
        this.PASS = PropertiesUtil.getProperty("sys.config.oracle.password");
    }

    public void initConnection() {
        try {
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接MySQL数据库...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println("实例化MySQL对象...");
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

}
