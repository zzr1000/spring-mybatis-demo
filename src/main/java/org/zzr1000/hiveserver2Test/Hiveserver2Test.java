package org.zzr1000.hiveserver2Test;

import org.apache.hive.jdbc.HiveStatement;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Properties;


public class Hiveserver2Test {

    private static String driverName =
            "org.apache.hive.jdbc.HiveDriver";

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

        String JDBC_URL="jdbc:hive2://xx.xx.xx.xx:10000/c";

        Connection con = DriverManager.getConnection(
                JDBC_URL, info);
//        Statement stmt = con.createStatement();
        HiveStatement stmt = (HiveStatement) con.createStatement();
//        String tableName = "wyphao";
//        stmt.execute("drop table if exists " + tableName);
//        stmt.execute("create table " + tableName +
//                " (key int, value string)");
//        System.out.println("Create table success!");
//        // show tables
        String sql = "select student.no,people.no from student , people where student.no = people.no";
//        System.out.println("Running: " + sql);
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


        // describe table
//        sql = "describe " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1) + "\t" + res.getString(2));
//        }
//
//
//        sql = "select * from " + tableName;
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(String.valueOf(res.getInt(1)) + "\t"
//                    + res.getString(2));
//        }
//
//        sql = "select count(1) from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }
    }
}
