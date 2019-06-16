package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;

import java.io.IOException;

public class HbaseTest4 {//不预分区、插单条记录

    //注意rowkey字段中,int类型的1 和字符串类型"1"在hbase shell中的存储区别:
    //string 1: 1; int 1:  \x00\x00\x00\x01
    //插入之前,将rowkey转换为string类型,应该就不会有热点问题了
    public static void main(String[] args) throws IOException {

        String tableName = "t0616v1";
//        createTable(tableName,"cf1");
        insertData(tableName,1,"cf1","name","zs1");

    }



    private static Configuration configuration;
    private static Connection hbaseConnection;

    static {
        PropertyLoadTest.initProperties("config.properties");
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", PropertyLoadTest.getConfigValue("hbase.zookeeper.quorum"));
        configuration.set("zookeeper.znode.parent", "/hbase");
    }

    public static void getConnection() throws IOException {
        hbaseConnection = ConnectionFactory.createConnection(configuration);
    }

    public static void closeConnection() throws IOException {
        if (hbaseConnection != null) {
            hbaseConnection.close();
        }
    }

    //普通创建表方法
    public static void createTable(String tableName,String...cf) throws IOException {
        System.out.println("start create table ......");
        try {
            //HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);// 这种写法已不推荐
            //Connection hbaseConnection = ConnectionFactory.createConnection(configuration);
            getConnection();
            HBaseAdmin hBaseAdmin =(HBaseAdmin) hbaseConnection.getAdmin();

            if (hBaseAdmin.tableExists(tableName)) {   // 如果存在要创建的表，那么先删除，再创建
                hBaseAdmin.disableTable(tableName);
                hBaseAdmin.deleteTable(tableName);
                System.out.println(tableName + " is exist,detele....");
            }

            //HTableDescriptor td = new HTableDescriptor(tableName); // 这种写法已不推荐使用
            HTableDescriptor td = new HTableDescriptor(TableName.valueOf(tableName));
            //td.addFamily(new HColumnDescriptor("cf1"));
            //td.addFamily(new HColumnDescriptor("cf2"));
            //td.addFamily(new HColumnDescriptor("cf3"));
            for (int i = 0; i < cf.length; i++) {
                td.addFamily(new HColumnDescriptor(cf[i]));
            }
            hBaseAdmin.createTable(td);
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        System.out.println("end create table ......");
    }

    //单次插入单条记录
    public static void insertData(String tableName ,
                                  int rowKey ,
                                  String columnFamily ,
                                  String qualifier,
                                  String value) throws IOException {
        //Connection hbaseConnection = ConnectionFactory.createConnection(configuration);
        getConnection();
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        table.put(put);
        table.close();
        closeConnection();
    }


}
