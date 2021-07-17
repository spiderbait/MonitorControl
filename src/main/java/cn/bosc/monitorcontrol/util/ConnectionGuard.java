package cn.bosc.monitorcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class ConnectionGuard {
    static final Logger logger = LoggerFactory.getLogger(ConnectionGuard.class);

    public static void getConnectionCount() {

        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String cmd = "sh -c netstat -an | grep -v grep | grep " + pid + " | wc -l";
        logger.info("Executed command = " + cmd);

    }



    public static void main(String[] args) {
        ConnectionGuard.getConnectionCount();
    }
}
