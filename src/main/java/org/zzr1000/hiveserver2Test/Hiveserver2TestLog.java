package org.zzr1000.hiveserver2Test;

import org.apache.hive.jdbc.HiveStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class Hiveserver2TestLog {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Properties info = new Properties();

        info.setProperty("user", "hive");
        info.setProperty("password", "");
        info.setProperty("hiveconf:tez.queue.name", "vvv");

        String JDBC_URL="jdbc:hive2://x.x.x.x:10000/c";

        Connection con = DriverManager.getConnection(
                JDBC_URL, info);
//      Statement stmt = con.createStatement();
        HiveStatement stmt = (HiveStatement) con.createStatement();

        String sql = "select student.no,people.no from student , people where student.no = people.no";

        ResultSet res = stmt.executeQuery(sql);
//        if (res.next()) {
//            System.out.println(res.getString(1));
//        }

//        String yarn_app_id = "";
//        for (String log : stmt.getQueryLog()) {
//            if (log.contains("App id")){
//                yarn_app_id = log.substring(log.indexOf("App id") +7, log.length()-1);
//            }
//        }
//        System.out.println("YARN Application ID: " + yarn_app_id);

        System.out.println("========begin:print job log========");

        for (String log : stmt.getQueryLog()) {
            System.out.println(log);
        }

        System.out.println("========end:print job log========");

    }
}

