package cn.bosc.monitorcontrol.entity;

public class Rule {

    int id;
    int mid;
    String type;
    String span_start;
    String span_end;
    String point;

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

    public String getSpan_start() {
        return span_start;
    }

    public void setSpan_start(String span_start) {
        this.span_start = span_start;
    }

    public String getSpan_end() {
        return span_end;
    }

    public void setSpan_end(String span_end) {
        this.span_end = span_end;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
