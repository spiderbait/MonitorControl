package cn.bosc.monitorcontrol.util;

import cn.bosc.monitorcontrol.constant.QuerySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StringUtil {

    public static String jobNameJoin(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String delimiter = ",";
        int i = 0;
        for (String s: list) {
            if (i == list.size() - 1) {
                sb.append("'").append(s).append("'");
            } else {
                s = s.replace("'", "");
                sb.append("'").append(s).append("'").append(delimiter).append("\n");
            }
            i ++;
        }
        return sb.toString();
    }

    public static String parseBatchInfo(ResultSet rs) throws SQLException {
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append(rs.getString("ETL_JOB")).append(",");
            sb.append(rs.getString("LAST_TXDATE")).append(",");
            sb.append(rs.getString("LAST_JOBSTATUS")).append("\n");
        }
        return sb.toString();
    }

    public static boolean validateWhereClause(String whereClause) {
        return whereClause.split(" ")[0].toUpperCase().equals("WHERE");
    }

    public static String getQueryString(String whereClause, List<String> jobList) {
        return QuerySQL.ETL_JOB_QUERY_BASE +
                " " + whereClause.toUpperCase() +
                " AND ETL_JOB IN (" +
                StringUtil.jobNameJoin(jobList).toUpperCase() + ")";
    }

    public static String getProbeQueryString(List<String> jobList) {
        return QuerySQL.ETL_JOB_QUERY_BASE +
                " WHERE ETL_JOB in (" +
                StringUtil.jobNameJoin(jobList).toUpperCase() + ") ORDER BY LAST_TXDATE DESC";
    }
}
