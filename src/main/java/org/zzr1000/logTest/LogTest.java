package org.zzr1000.logTest;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private static Logger logger = LoggerFactory.getLogger(LogTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        // System.out.println("This is println message.");

        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("C:\\Users\\zrz\\IdeaProjects\\spring-mybatis-demo\\src\\main\\resources\\log4j.properties");

        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
}
