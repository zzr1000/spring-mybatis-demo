package org.zzr1000.hbaseTest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HbaseTestFromTo2 {

    private static Configuration configuration;
    private static Connection hbaseConnection;
//    private static List<Put> batchPutList = new ArrayList<Put>();//批量put

    static {//两种方式：一种是指定这三个需要的参数 ; 二是使用hbase-site.xml文件 ; 第一种情况下,参数可以直接指定,也可以通过配置文件获得
        configuration = HBaseConfiguration.create();
        //PropertyLoadTest.initProperties("config.properties");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        configuration.set("hbase.zookeeper.quorum", "114.67.69.229");
        configuration.set("hbase.zookeeper.quorum", "xxxxx");
        configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
    }

    public static void main(String[] args) throws Exception{

//        String tableName = "t0625";
//        createTablePreSplit(tableName);
        getAll("bdm_hzw_scm_hzw_scm_req_summary_detail_de_2019-06-24");
//        batchPutTest("t0625");

    }

    public static void getAll(String tableName) throws IOException {
        getConnection();
        Admin admin=hbaseConnection.getAdmin();
        Table table = hbaseConnection.getTable(TableName.valueOf(tableName));

        byte[] sk = Bytes.toBytes("1");
        byte[] ek = Bytes.toBytes("10000");
        Scan scan = new Scan();
//        scan.setStartRow(sk);
//        scan.setStopRow(ek);
//        scan.setMaxResultSize(100);

        ResultScanner scanner = table.getScanner(scan);
//      List<Map<String, Object>> list = new ArrayList<>();

        int flag = 0;
        List<Put> batchPutList = new ArrayList<Put>();
        for (Result result : scanner) {
//            Map<String, Object> tmpMap = resultToMap(r);
//            list.add(tmpMap);
//            for (Map.Entry<String, Object> entry : tmpMap.entrySet()) {
//                System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
//            }

            flag ++;//1000条记录，batchPut一次，防止内存溢出



            String temprow = null;
            byte[] rowkey = null;

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
                temprow = Bytes.toString(temprowarray);//rowname


                byte[] tempqulifierarray = new byte[qualifierlength];
                System.arraycopy(qualifierArray, qualifieroffset, tempqulifierarray, 0, qualifierlength);
                String tempqulifier = Bytes.toString(tempqulifierarray);

                byte[] tempfamilyarray = new byte[familylength];
                System.arraycopy(familyArray, familyoffset, tempfamilyarray, 0, familylength);
                String tempfamily = Bytes.toString(tempfamilyarray);

                byte[] tempvaluearray = new byte[valuelength];
                System.arraycopy(valueArray, valueoffset, tempvaluearray, 0, valuelength);
                String tempvalue = Bytes.toString(tempvaluearray);

                String pre = String.valueOf(Integer.valueOf(DigestUtils.md5Hex(temprow).substring(31),16)%10);
                rowkey = Bytes.toBytes(pre + "-" + temprow);
                Put put = new Put(rowkey);

                put.addColumn(tempfamilyarray,tempqulifierarray,tempvaluearray);

                batchPutList.add(put);
            }

            Put put = new Put(rowkey);
            put.addColumn("info".getBytes(),"id".getBytes(),temprow.getBytes());
            batchPutList.add(put);

            if(flag == 1000){
                batchPutTest("t0625",batchPutList);
                batchPutList = new ArrayList<Put>();
                flag = 0;
            }

        }
        scanner.close();
        admin.close();
        table.close();
        closeConnection();
//        System.out.println(">>>>>> " + tableName + " 查询成功! total=" + list.size());
//        System.out.println("data:"+ JSONObject.toJSONString(list));
//        return list;
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


    /**
     * 把result转换成map，方便返回json数据
     *
     * @param result
     * @return
     */
    public static Map<String, Object> resultToMap(Result result) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<Cell> listCell = result.listCells();
        Map<String, Object> tempMap = new HashMap<String, Object>();
        String rowname = "";
        List<String> familynamelist = new ArrayList<String>();
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
            String temprow = Bytes.toString(temprowarray);

            byte[] tempqulifierarray = new byte[qualifierlength];
            System.arraycopy(qualifierArray, qualifieroffset, tempqulifierarray, 0, qualifierlength);
            String tempqulifier = Bytes.toString(tempqulifierarray);

            byte[] tempfamilyarray = new byte[familylength];
            System.arraycopy(familyArray, familyoffset, tempfamilyarray, 0, familylength);
            String tempfamily = Bytes.toString(tempfamilyarray);

            byte[] tempvaluearray = new byte[valuelength];
            System.arraycopy(valueArray, valueoffset, tempvaluearray, 0, valuelength);
            String tempvalue = Bytes.toString(tempvaluearray);


            tempMap.put(tempfamily + ":" + tempqulifier, tempvalue);
            rowname = temprow;
            String familyname = tempfamily;
            if (familynamelist.indexOf(familyname) < 0) {
                familynamelist.add(familyname);
            }
        }
        resMap.put("rowname", rowname);
        for (String familyname : familynamelist) {
            HashMap<String, Object> tempFilterMap = new HashMap<String, Object>();
            for (String key : tempMap.keySet()) {
                String[] keyArray = key.split(":");
                if (keyArray[0].equals(familyname)) {
                    tempFilterMap.put(keyArray[1], tempMap.get(key));
                }
            }
            resMap.put(familyname, tempFilterMap);
        }
        return resMap;
    }


    //获得批量的put
    private static List<Put> batchPut() {
        List<Put> list = new ArrayList<Put>();
        for (int i = 1; i <= 10000; i++) {
            byte[] rowkey = Bytes.toBytes(getRandomNumber() + "-" + System.currentTimeMillis() + "-" + i);
            Put put = new Put(rowkey);
            put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("name"), Bytes.toBytes("zs" + i));
            list.add(put);
        }
        return list;
    }


    //md5取模：
    public static String getRandomNumber() {
        String ranStr = Math.random() + "";
        //System.out.println(ranStr);
        int pointIndex = ranStr.indexOf(".");
        //System.out.println(ranStr.substring(pointIndex + 1, pointIndex + 3));
        return ranStr.substring(pointIndex + 1, pointIndex + 3);
    }

    //批量插入
    public static void batchPutTest(String toTable,List<Put> batchPutList) throws IOException {
        getConnection();
        Table table = hbaseConnection.getTable(TableName.valueOf(toTable));
//        table.put(batchPut());
        table.put(batchPutList);
        table.close();
        closeConnection();
    }

    public static void createTablePreSplit(String tableName) throws IOException {
        getConnection();
        //获取admin实例
        Admin admin = hbaseConnection.getAdmin();
        //创建表描述符
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        //添加列族描述符到表描述符中
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(Bytes.toBytes("info"));
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
}
