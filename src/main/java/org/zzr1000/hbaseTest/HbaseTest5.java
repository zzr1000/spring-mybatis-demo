package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;

import java.io.IOException;

public class HbaseTest5 {//测试不同类型数据,在hbase中的存储情况


    public static void main(String[] args) throws IOException {

        String tableName = "t0616v1";
//        createTable(tableName,"cf1");
//        insertData(tableName,1,"cf1","name","zs1");
        testDataType(tableName,1,"cf1","name","zs1");

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

    //测试不同数据类型在hbase中的存储情况
    public static void testDataType(String tableName ,
                                  int rowKey ,
                                  String columnFamily ,
                                  String qualifier,
                                  String value) throws IOException {

        System.out.println(rowKey);
        System.out.println(String.valueOf(rowKey));


        byte[] rk1 = Bytes.toBytes(rowKey);

        for (byte r : rk1) {
            System.out.print(r);
            System.out.print("=");
        }

        System.out.println();

        byte[] rk2 = Bytes.toBytes(String.valueOf(rowKey));

        //string英文字符占一个字节;中文字符占字符情况，与字符集有关
        System.out.println(rk2.length);

        for (byte r : rk2) {
            System.out.print(r);
            System.out.print("=");//打印49=
        }


    }
    //直接使用java的byte类型测试、查看
    //参考typeTest包
    //也验证了hbase工具包中的Byte类和java原生的转换是相同的
    //得到一个结论：
    // int 1 对应的byte就是0001（4个字节）
    // string 1 或者 char 1,对应1的ascii码 49（1个字节）
    @Test
    public  void typeTest() {
        //java原生
        byte[] bt = "1".getBytes();
        for(byte b:bt){
            System.out.println(b);//49：字符'1'的ascii码
        }
        //hbase util
        byte[] bt2 = Bytes.toBytes("1");
        for(byte b:bt){
            System.out.println(b);//49：字符'1'的ascii码
        }
    }
}
