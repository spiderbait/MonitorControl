package cn.bosc.monitorcontrol.dao;
import cn.bosc.monitorcontrol.util.PropertiesUtil;

import java.sql.*;

public class MySQLConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = PropertiesUtil.getProperty("sys.config.mysql.url");
    static final String USER = PropertiesUtil.getProperty("sys.config.mysql.user");
    static final String PASS = PropertiesUtil.getProperty("sys.config.mysql.password");
    Connection conn = null;
    Statement stmt = null;

    public MySQLConnector() {
        try {
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接MySQL数据库...");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化MySQL对象...");
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

//    public static void main(String[] args) {
//        Connection conn = null;
//        Statement stmt = null;
//        try{
//            // 注册 JDBC 驱动
//            Class.forName(JDBC_DRIVER);
//
//            // 打开链接
//            System.out.println("连接数据库...");
//            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//
//            // 执行查询
//            System.out.println(" 实例化Statement对象...");
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT * FROM List";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            Field[] fields = List.class.getDeclaredFields();
//            for (Field field: fields) {
//                System.out.println(field.getType().getName());
//                System.out.println(field.getName());
//            }
//
//            // 展开结果集数据库
//            while(rs.next()){
//                // 通过字段检索
//                int id  = rs.getInt("mid");
//                String name = rs.getString("name");
//                String url = rs.getString("url");
//
//                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
//                System.out.print("\n");
//            }
//            // 完成后关闭
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch(Exception e){
//            // 处理 JDBC 错误
//            e.printStackTrace();
//        }// 处理 Class.forName 错误
//        finally{
//            // 关闭资源
//            try{
//                if(stmt != null) stmt.close();
//            }catch(SQLException sqle){
//                sqle.printStackTrace();
//            }// 什么都不做
//            try{
//                if(conn != null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        System.out.println("Goodbye!");
//    }
}
