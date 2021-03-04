package cn.bosc.monitorcontrol.dao;

import java.util.List;

public class BatchInfoFetcher {

    private final OracleConnector oc;

    public BatchInfoFetcher() {
        oc = new OracleConnector();
    }

    public String getDelayedBatchTPlus1(List<String> jobList) {
        String sql = "SELECT ETL_JOB FROM ETL_JOB WHERE AND ETL_JOB IN ";
        return "";
    }




}
