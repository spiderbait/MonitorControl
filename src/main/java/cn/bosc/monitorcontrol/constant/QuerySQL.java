package cn.bosc.monitorcontrol.constant;

public class QuerySQL {
    public static final String ETL_JOB_QUERY_BASE = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB";
    public static final String ETL_JOB_COUNT_BASE = "SELECT COUNT(*) FROM ETL_JOB";
}
