package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.math.BigInteger;

public class HbaseTest {

    private static Configuration configuration;
    private static Connection hbaseConnection;

    static {//两种方式：一种是指定这三个需要的参数 ; 二是使用hbase-site.xml文件 ; 第一种情况下,参数可以直接指定,也可以通过配置文件获得
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "xx");
        configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
    }

    public static void main(String[] args) throws Exception{
        //createTable("t0614","cf1","cf2");
        //insertData("t0614","r1","cf1","c1","v1");
        // QueryAll("wujintao");
        // QueryByCondition1("wujintao");
        // QueryByCondition2("wujintao");
        //QueryByCondition3("wujintao");
        //deleteRow("wujintao","abcdef");
        //deleteByCondition("wujintao","abcdef");
    }

    //抽象出获得connection和关闭connection的方法,防止连接泄露
    public static void getConnection() throws IOException {
        hbaseConnection = ConnectionFactory.createConnection(configuration);
    }

    public static void closeConnection() throws IOException {
        if (hbaseConnection != null) {
            hbaseConnection.close();
        }
    }

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

    public static void createTablePreSplit(String tableName,String...cf) throws Exception {
      //Connection hbaseConnection = ConnectionFactory.createConnection(configuration);
        getConnection();
        HBaseAdmin admin = (HBaseAdmin)hbaseConnection.getAdmin();
        if (admin.tableExists(Bytes.toBytes(tableName))) {//判断表是否存在
            System.out.println(">>>>>>" + tableName + " 表已经存在");
        } else {
            System.out.println(">>>>>>开始创建表 " + tableName);
            HTableDescriptor td = new HTableDescriptor(TableName.valueOf(tableName));
            for (int i = 0; i < cf.length; i++) {
                HColumnDescriptor family = new HColumnDescriptor(cf[i]);
                td.addFamily(family);
            }
            byte[][] splits =getHexSplits("100000000000000000", "ffffffffffffffffffff",
                    300);
            admin.createTable(td,splits);
            if (admin.tableExists(Bytes.toBytes(tableName))) {
                System.out.println(">>>>>>表 " + tableName + ",创建成功！");
            } else {
                System.out.println(">>>>>>表 " + tableName + ",创建失败！");
                throw new RuntimeException(">>>>>>表 " + tableName + ",创建失败！");
            }
        }
        closeConnection();
    }

    public static void insertData(String tableName ,
                                  String rowKey ,
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




    public static byte[][] getHexSplits(String startKey, String endKey, int numRegions) {
        byte[][] splits = new byte[numRegions-1][];
        BigInteger lowestKey = new BigInteger(startKey, 16);
        BigInteger highestKey = new BigInteger(endKey, 16);
        BigInteger range = highestKey.subtract(lowestKey);
        BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
        lowestKey = lowestKey.add(regionIncrement);
        for(int i=0; i < numRegions-1;i++) {
            BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
            byte[] b = String.format("%016x", key).getBytes();
            splits[i] = b;
        }
        return splits;
    }


}
