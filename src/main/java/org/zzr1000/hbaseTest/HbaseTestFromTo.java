package org.zzr1000.hbaseTest;


import org.apache.commons.codec.digest.DigestUtils;
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

        //下面对于scanner的循环，如果一次循环完，在插入，会创建一个非常大的batchPutList
        //有可能会造成oom，可以设置一个flag，当数据大于xxxx值的时候，就执行一个batchPut
        //然后将list置为空，在执行后续重复操作：. .
        //这些，都是一些实际测试实践过程中的一些小的优化：这些优化，都是没有什么技巧，测试
        //实践中，遇到了对应问题，就想到这种解决方案：...

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

                //增加md取模散列//还缺少rowkey字段，在列族中的展示
                String pre = String.valueOf(Integer.valueOf(DigestUtils.md5Hex(temprow).substring(31),16)%10);
                byte[] rowkey = Bytes.toBytes(pre + "-" + temprow);
                Put put = new Put(rowkey);

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
