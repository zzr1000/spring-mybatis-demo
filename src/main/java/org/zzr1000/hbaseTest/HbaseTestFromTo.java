package org.zzr1000.hbaseTest;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//将hbase一个表的数据，对特定字段，做一些处理：比如rowky：md5散列 等：.
//正常情况下，可以从kafka等其他来源过来，在插入hbase目标表之前，做对应处理
//从一个hbase表，导入到另外一个hbase表的情况，可作为验证：
public class HbaseTestFromTo {

    public static void main(String[] args) throws IOException {
        getAll("fromTable");
        batchPutTest("toTable");
    }

    //静态变量、静态方法、静态代码块：
    private static Configuration configuration;
    private static Connection hbaseConnection;
    private static List<Put> batchPutList = new ArrayList<Put>();//批量put

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort","2181");
        configuration.set("hbase.zookeeper.quorum", "xxxxx");
        configuration.set("zookeeper.znode.parent", "/hbase");
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

    public static void getAll(String tableName) throws IOException {
        getConnection();
        Admin admin=hbaseConnection.getAdmin();
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));

        byte[] sk = Bytes.toBytes("1");
        byte[] ek = Bytes.toBytes("10000");
        Scan scan = new Scan();
        scan.setStartRow(sk);
        scan.setStopRow(ek);
        scan.setMaxResultSize(100);

        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            List<Cell> listCell = result.listCells();
            for (Cell cell : listCell) {
                byte[] rowArray = cell.getRowArray();
                byte[] familyArray = cell.getFamilyArray();
                byte[] qualifierArray = cell.getQualifierArray();
                byte[] valueArray = cell.getValueArray();
                int rowoffset = cell.getRowOffset();
                int familyoffset = cell.getFamilyOffset();
                int qualifieroffset = cell.getQualifierOffset();
                int valueoffset = cell.getValueOffset();
                int rowlength = cell.getRowLength();
                int familylength = cell.getFamilyLength();
                int qualifierlength = cell.getQualifierLength();
                int valuelength = cell.getValueLength();

                byte[] temprowarray = new byte[rowlength];
                System.arraycopy(rowArray, rowoffset, temprowarray, 0, rowlength);
                String temprow = Bytes.toString(temprowarray);//rowname：字节转字符串


                byte[] tempqulifierarray = new byte[qualifierlength];
                System.arraycopy(qualifierArray, qualifieroffset, tempqulifierarray, 0, qualifierlength);
                String tempqulifier = Bytes.toString(tempqulifierarray);

                byte[] tempfamilyarray = new byte[familylength];
                System.arraycopy(familyArray, familyoffset, tempfamilyarray, 0, familylength);
                String tempfamily = Bytes.toString(tempfamilyarray);

                byte[] tempvaluearray = new byte[valuelength];
                System.arraycopy(valueArray, valueoffset, tempvaluearray, 0, valuelength);
                String tempvalue = Bytes.toString(tempvaluearray);

                Put put = new Put(temprowarray);
                put.addColumn(tempfamilyarray,tempqulifierarray,tempvaluearray);

                batchPutList.add(put);
            }
        }
        scanner.close();
        admin.close();
        table.close();
        closeConnection();
    }

    //批量插入
    public static void batchPutTest(String toTable) throws IOException {
        getConnection();
        Table table = hbaseConnection.getTable(TableName.valueOf(toTable));
        table.put(batchPutList);
        table.close();
        closeConnection();
    }

}
