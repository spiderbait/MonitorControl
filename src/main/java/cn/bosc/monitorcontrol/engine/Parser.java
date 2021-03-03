package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.dao.EntityFetcher;
import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.SQLException;
import java.util.List;

public class Parser {
    EntityFetcher ef;
    List<cn.bosc.monitorcontrol.entity.List> lists;
    List<Rule> rules;

    public Parser() {
        this.ef = new EntityFetcher();
    }

    public void parseList() {
        try {
            lists = this.ef.getLists();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void parseRule(int mid) {
        try {
            rules = this.ef.getRules();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
