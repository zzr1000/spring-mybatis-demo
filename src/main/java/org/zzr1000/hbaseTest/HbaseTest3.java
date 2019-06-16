package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseTest3 {

    //这个测试结果,请求都打在了第一个region上,即没有startkey的那个region
    //rowkey如果转换为string,就不会有热点了,
    //注意指定的region分界符和插入数据的类型
    public static void main(String[] args) throws IOException {
        String tableName = "t0616";
        createTablePreSplit(tableName);
        putTest(tableName);
        closeConnection();
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


    //预分区示例4：输入固定的预分区数组
    public static void createTablePreSplit(String tableName) throws IOException {
        getConnection();
        //获取admin实例
        Admin admin = hbaseConnection.getAdmin();
        //创建表描述符
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        //添加列族描述符到表描述符中
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(Bytes.toBytes("cf1"));
        tableDescriptor.addFamily(columnDescriptor);

        //创建表中region的拆分行键
        //注意hbase中string类型和int类型的区别
        //region拆分界限是string,但是插入的时候是int,就会有问题
        byte[][] regions = new byte[][] {
                Bytes.toBytes("0|"),
                Bytes.toBytes("1|"),
                Bytes.toBytes("2|"),
                Bytes.toBytes("3|"),
                Bytes.toBytes("4|"),
                Bytes.toBytes("5|"),
                Bytes.toBytes("6|"),
                Bytes.toBytes("7|"),
                Bytes.toBytes("8|"),
                Bytes.toBytes("9|")
        };
        //tableDescriptor.setName(TableName.valueOf(tableName));
        //使用新表明和region的已拆分键值列表作为参数调用建表命令
        //使用已拆分行键的集合：使用了已经拆分好的region边界列表，因此结果都是与预期相符的。
        admin.createTable(tableDescriptor, regions);
        closeConnection();
    }

    //批量插入
    public static void putTest(String tableName) throws IOException {
        getConnection();
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
        table.put(batchPut());
        table.close();
        closeConnection();
    }

    //获得批量的put
    private static List<Put> batchPut() {
        List<Put> list = new ArrayList<Put>();
        for (int i = 1; i <= 10000; i++) {
          //byte[] rowkey = Bytes.toBytes(getRandomNumber() + "-" + System.currentTimeMillis() + "-" + i);
          //byte[] rowkey = Bytes.toBytes(i);//这样写,会有数据热点
            byte[] rowkey = Bytes.toBytes(String.valueOf(i));//这样数据是均匀的
            Put put = new Put(rowkey);
            put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("name"), Bytes.toBytes("zs" + i));
            list.add(put);
        }
        return list;
    }

}
