package cn.bosc.monitorcontrol.engine;

import cn.bosc.monitorcontrol.dao.EntityFetcher;
import cn.bosc.monitorcontrol.entity.Rule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {
    EntityFetcher ef = new EntityFetcher();
    List<cn.bosc.monitorcontrol.entity.List> lists;
    HashMap<Integer, List<Rule>> ruleMap = new HashMap<>();
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
                if (!this.ruleMap.containsKey(rule.getMid())) {
                    List<Rule> list = new ArrayList<>();
                    list.add(rule);
                } else {
                    this.ruleMap.get(rule.getMid()).add(rule);
                }
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

    public HashMap<Integer, List<Rule>> getRuleMap() {
        return ruleMap;
    }
}
