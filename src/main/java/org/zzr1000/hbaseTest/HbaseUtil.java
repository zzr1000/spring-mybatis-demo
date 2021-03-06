package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseUtil {



    private static Configuration configuration;
    private static Connection hbaseConnection;


    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "bigdata-test-01,bigdata-test-02,bigdata-test-03");
        configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
    }


    //单次插入单条记录
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

    //判断rk是否存在
    public static Boolean isRowKeyExist(String tableName, String rowKey) throws IOException {

        getConnection();
        Boolean b ;
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        b = rs.isEmpty();
        closeConnection();
        return !b;
    }



    //QueryByRowkey
    public static void QueryByRK(String tableName , String rowKey) throws IOException {

        getConnection();
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result r = table.get(get);

        if(!r.isEmpty()) {
            System.out.println("rowkey:" + new String(r.getRow()));
            for (KeyValue keyValue : r.raw()) {
                System.out.println("列：" + new String(keyValue.getFamily())
                        + "====值:" + new String(keyValue.getValue()));
            }
        }
    }


    public static void getConnection() throws IOException {
        hbaseConnection = ConnectionFactory.createConnection(configuration);
    }

    public static void closeConnection() throws IOException {
        if (hbaseConnection != null) {
            hbaseConnection.close();
        }
    }


}
