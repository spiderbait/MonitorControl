package cn.bosc.monitorcontrol.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static final String propertiesPath = "monitor_control.properties";

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(propertiesPath));
            properties.load(bufferedReader);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
