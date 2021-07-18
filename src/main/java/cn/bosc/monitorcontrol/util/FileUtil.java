package cn.bosc.monitorcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileUtil {
    private static String baseOutputPath = "/automation/LOG/data_monitoring/";
    static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static void detectOutputFolder(String systemName) {
        File file = new File(baseOutputPath + systemName.toLowerCase());
        if (!file.exists()) { //用来测试此路径名表示的文件或目录是否存在
            System.out.println(file.isDirectory()); //来判断这是不是一个文件夹。
            System.out.println(file.mkdirs());
        }
    }

    public static void main(String[] args) {
        FileUtil.detectOutputFolder("asd");
    }
}
