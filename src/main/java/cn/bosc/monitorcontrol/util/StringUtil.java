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
        if (whereClause.toUpperCase().equals("WHERE COUNT")) {
            return QuerySQL.ETL_JOB_COUNT_BASE +
                    " WHERE ETL_JOB IN (" +
                    StringUtil.jobNameJoin(jobList).toUpperCase() +
                    ") AND LAST_TXDATE<TO_DATE(TO_CHAR(SYSDATE-1, 'yyyymmdd'), 'yyyymmdd')";
        } else {
            return QuerySQL.ETL_JOB_QUERY_BASE +
                    " " + whereClause.toUpperCase() +
                    " AND ETL_JOB IN (" +
                    StringUtil.jobNameJoin(jobList).toUpperCase() + ")";
        }
    }

    public static String getProbeQueryString(List<String> jobList) {

        return "\n使用以下语句查询作业的最新状态：\n" +
                QuerySQL.ETL_JOB_QUERY_BASE +
                " WHERE ETL_JOB in (" +
                StringUtil.jobNameJoin(jobList).toUpperCase() +
                ") ORDER BY LAST_TXDATE DESC";

    }
}
