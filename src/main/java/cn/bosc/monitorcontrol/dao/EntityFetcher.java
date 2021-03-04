package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityFetcher {

    private final MySQLConnector cb;

    public EntityFetcher() {
        cb = new MySQLConnector();
    }

    public List<cn.bosc.monitorcontrol.entity.List> getLists() throws SQLException {
        ResultSet rs = cb.execQuery("select * from MonitorControl.list");
        List<cn.bosc.monitorcontrol.entity.List> result = new ArrayList<cn.bosc.monitorcontrol.entity.List>();
        while(rs.next()){
            //
            cn.bosc.monitorcontrol.entity.List row = new cn.bosc.monitorcontrol.entity.List();
            row.setMid(rs.getInt("mid"));
            row.setName(rs.getString("name"));
            row.setDescription(rs.getString("description"));
            result.add(row);
        }

        return result;
    }

    public List<Rule> getRules() throws SQLException{
        ResultSet rs = cb.execQuery("select * from MonitorControl.rule");
        List<Rule> result = new ArrayList<Rule>();
        while(rs.next()){
            //
            Rule row = new Rule();
            row.setId(rs.getInt("id"));
            row.setMid(rs.getInt("mid"));
            row.setType(rs.getString("type"));
            row.setSpan(rs.getString("span"));

            result.add(row);
        }

        return result;
    }


}
