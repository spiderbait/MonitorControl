package cn.bosc.monitorcontrol.dao;

import cn.bosc.monitorcontrol.constant.Constant;
import cn.bosc.monitorcontrol.util.StringUtil;
import cn.bosc.monitorcontrol.util.TimestampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BatchInfoFetcher {

//    private final OracleConnector oc;
    Logger logger = LoggerFactory.getLogger(BatchInfoFetcher.class);

//    public BatchInfoFetcher() {
//        oc = new OracleConnector();
//    }

    public String getBatchInfo(String whereClause, List<String> jobList) throws SQLException{

        if (jobList.size() == 0 || !StringUtil.validateWhereClause(whereClause)) {
            return "监控作业清单或where语句配置有误，请检查。";
        }

        StringBuilder sb = new StringBuilder();
        OracleConnector oc = new OracleConnector();
        oc.getConnection();
        ResultSet rs = oc.execQuery(StringUtil.getQueryString(whereClause, jobList));

        if (whereClause.toUpperCase().startsWith("WHERE COUNT")) {
            logger.info("Count query fetched.");
            int n = 0;
            int threshold = Integer.parseInt(whereClause.split(" ")[2].trim());
            while (rs.next()) {
                n = rs.getInt("COUNT(*)");
            }

            logger.debug("Count threshold = " + threshold + ", count(*) = " + n);
            if (n < threshold) {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" ERROR\n");
                sb.append("\n作业完成数未到达设定数值：\n").append(n);
            } else {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" 当前监控作业运行正常。\n");
            }
        } else {
            String out = StringUtil.parseBatchInfo(rs);

            if (out.length() > 0) {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" ERROR\n");
                sb.append("\n如下作业存在问题：\n").append(out);
            } else {
                sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" 当前监控作业运行正常。\n");
            }
        }
        oc.closeConnection();
        return sb.toString();
    }

    @Deprecated
    public String getDelayedBatch(String whereClause, List<String> jobList) throws SQLException {

        if (jobList.size() == 0 || !StringUtil.validateWhereClause(whereClause)) {
            return "监控作业清单或where语句配置有误，请检查。";
        }

        StringBuilder sb = new StringBuilder();

        String query = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE LAST_TXDATE<TO_DATE(TO_CHAR(SYSDATE-1, 'yyyymmdd'), 'yyyymmdd') AND ETL_JOB IN ("
                + StringUtil.jobNameJoin(jobList) + ")";
        OracleConnector oc = new OracleConnector();
        oc.getConnection();
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
        oc.closeConnection();
        return sb.toString();
    }

    @Deprecated
    public String getFailedBatch(String whereClause, List<String> jobList) {
        if (jobList.size() == 0 || !StringUtil.validateWhereClause(whereClause)) {
            return "监控作业清单或where语句配置有误，请检查。";
        }

        StringBuilder sb = new StringBuilder();
        OracleConnector oc = new OracleConnector();
        try {
            String query = "SELECT ETL_JOB, LAST_TXDATE, LAST_JOBSTATUS FROM ETL_JOB WHERE LAST_JOBSTATUS='Failed' AND ETL_JOB IN ("
                    + StringUtil.jobNameJoin(jobList) + ")";
            oc.getConnection();
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
            oc.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

//    public String getCount(List<String> jobList) {
//
//        if (jobList.size() == 0) {
//            return "监控作业清单或where语句配置有误，请检查。";
//        }
//
//        StringBuilder sb = new StringBuilder();
//        ResultSet rs = oc.execQuery(StringUtil.getQueryString(jobList));
//        String out = StringUtil.parseBatchInfo(rs);
//        String probe = StringUtil.getProbeQueryString(jobList);
//
//        if (out.length() > 0) {
//            sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" ERROR\n");
//            sb.append("\n如下作业存在T+1实效性问题：\n").append(out).append("\n可使用如下语句监控问题作业状态：\n").append(probe);
//        } else {
//            sb.append(TimestampUtil.getNowTimestampString(Constant.LOG_TS_FORMAT)).append(" 当前监控作业运行正常。\n");
//        }
//
//        return sb.toString();
//    }

}
