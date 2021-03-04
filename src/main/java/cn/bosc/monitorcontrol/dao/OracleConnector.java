package cn.bosc.monitorcontrol.dao;

import java.sql.*;

public class OracleConnector {
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    static final String USER = "system";
    static final String PASS = "oracle";
    Connection conn = null;
    Statement stmt = null;

    public OracleConnector(){
        try {
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
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

    public static void main(String[] args) {

    }
}
