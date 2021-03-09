package cn.bosc.monitorcontrol.entity;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    int id;
    int mid;
    String type;
    String name;
    String span;
    String path;
    List<String> jobList;
    String whereClause;

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String where_clause) {
        this.whereClause = where_clause;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public List<String> getJobList() {
        return jobList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobList(String jobListString) {
        List<String> jobList = new ArrayList<>();
        for (String s: jobListString.split(",")) {
            jobList.add(s.replace("'", "").trim());
        }
        this.jobList = jobList;
    }
}
