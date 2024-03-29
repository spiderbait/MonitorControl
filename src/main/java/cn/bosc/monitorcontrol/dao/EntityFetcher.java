package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.entity.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityFetcher {

    Logger logger = LoggerFactory.getLogger(EntityFetcher.class);

    public List<cn.bosc.monitorcontrol.entity.List> getLists() throws SQLException, ClassNotFoundException {
        MySQLConnector connector = new MySQLConnector();
        connector.getConnection();
        ResultSet rs = connector.execQuery("select * from list");
        logger.debug("Get list query executed.");
        List<cn.bosc.monitorcontrol.entity.List> result = new ArrayList<cn.bosc.monitorcontrol.entity.List>();
        while(rs.next()){
            cn.bosc.monitorcontrol.entity.List row = new cn.bosc.monitorcontrol.entity.List();
            row.setMid(rs.getInt("mid"));
            row.setName(rs.getString("name"));
            row.setDescription(rs.getString("description"));
            logger.debug("Get list: mid = " + rs.getInt("mid")
                    + ", name = " + rs.getString("name")
                    + ", description = " + rs.getString("description"));
            result.add(row);
        }
        connector.closeConnection();
        return result;
    }

    public List<Rule> getRules() throws SQLException{
        MySQLConnector connector = new MySQLConnector();
        connector.getConnection();
        ResultSet rs = connector.execQuery("select * from rule where enable=1");
        logger.debug("Get rule query executed.");
        List<Rule> result = new ArrayList<Rule>();
        while(rs.next()){
            Rule row = new Rule();
            row.setId(rs.getInt("id"));
            row.setMid(rs.getInt("mid"));
            row.setType(rs.getString("type"));
            row.setName(rs.getString("name"));
            row.setSpan(rs.getString("span"));
            row.setPath(rs.getString("path"));
            row.setJobList(rs.getString("job_list"));
            row.setWhereClause(rs.getString("where_clause"));
            row.setEndKeyword(rs.getString("end_keyword"));
            row.setReceivers(rs.getString("receivers"));
            logger.debug("Rule query: id = "
                    + rs.getInt("id") + ", mid = "
                    + rs.getInt("mid") + ", name = "
                    + rs.getString("name"));
            result.add(row);
        }
        connector.closeConnection();
        logger.debug("Get rule query connection detached.");
        return result;
    }

    public List<Rule> getRulesByMid(int mid) throws SQLException{
        MySQLConnector connector = new MySQLConnector();
        connector.getConnection();
        ResultSet rs = connector.execQuery("select * from rule where mid=" + mid + " and enable=1");
        List<Rule> result = new ArrayList<>();
        while(rs.next()){
            //
            Rule row = new Rule();
            row.setId(rs.getInt("id"));
            row.setMid(rs.getInt("mid"));
            row.setType(rs.getString("type"));
            row.setSpan(rs.getString("span"));
            row.setPath(rs.getString("path"));
            row.setJobList(rs.getString("job_list"));

            result.add(row);
        }
        connector.closeConnection();
        return result;
    }
}
