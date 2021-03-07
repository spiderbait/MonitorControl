package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.util.StringUtil;
import cn.bosc.monitorcontrol.util.TimestampUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BatchInfoFetcher {

    private final OracleConnector oc;

    public BatchInfoFetcher() {
        oc = new OracleConnector();
    }

    public String getDelayedBatchTPlus1(List<String> jobList) throws SQLException {

        if (jobList.size() == 0) {
            return "监控作业清单配置有误，请检查。";
        }

        StringBuilder sb = new StringBuilder();

        String query = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE LAST_TXDATE<TO_DATE(TO_CHAR(SYSDATE-1, 'yyyymmdd'), 'yyyymmdd') AND ETL_JOB IN ("
                + StringUtil.jobNameJoin(jobList) + ")";
        ResultSet rs = oc.execQuery(query);
        String probe = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE ETL_JOB IN ("
                + StringUtil.jobNameJoin(jobList) + ") ORDER BY LAST_TXDATE DESC;\n";
        String out = StringUtil.parseBatchInfo(rs);
        if (out.length() > 0) {
            sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" ERROR\n");
            sb.append("\n如下作业存在T+1实效性问题：\n").append(out).append("\n可使用如下语句监控问题作业状态：\n").append(probe);
        } else {
            sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" 当前监控作业运行正常。\n");
        }
        return sb.toString();
    }

    public String getFailedBatch(List<String> jobList) {
        if (jobList.size() == 0) {
            return "监控作业清单配置有误，请检查。";
        }

        StringBuilder sb = new StringBuilder();

        try {
            String query = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE LAST_JOBSTATUS='Failed' AND ETL_JOB IN ("
                    + StringUtil.jobNameJoin(jobList) + ")";
            ResultSet rs = oc.execQuery(query);
            String probe = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE ETL_JOB IN ("
                    + StringUtil.jobNameJoin(jobList) + ") ORDER BY LAST_TXDATE DESC;\n";
            String out = StringUtil.parseBatchInfo(rs);
            if (out.length() > 0) {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" ERROR\n");
                sb.append("\n如下作业运行出错：\n").append(out).append("\n可使用如下语句监控问题作业状态：\n").append(probe);
            } else {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" 当前监控作业运行正常。\n");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
