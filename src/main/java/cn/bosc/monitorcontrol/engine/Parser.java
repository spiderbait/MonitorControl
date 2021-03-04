package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.dao.EntityFetcher;
import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Parser {
    EntityFetcher ef = new EntityFetcher();
    List<cn.bosc.monitorcontrol.entity.List> lists;
    HashMap<Integer, Rule> ruleMap = new HashMap<Integer, Rule>();
    List<Rule> rules;

    private void parseList() {
        try {
            this.lists = this.ef.getLists();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void parseRule() {
        try {
            this.rules = this.ef.getRules();
            for (Rule rule: this.rules) {
                this.ruleMap.put(rule.getMid(), rule);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void parse() {
        parseList();
        parseRule();
    }

    public List<cn.bosc.monitorcontrol.entity.List> getLists() {
        return lists;
    }

    public HashMap<Integer, Rule> getRuleMap() {
        return ruleMap;
    }
}
