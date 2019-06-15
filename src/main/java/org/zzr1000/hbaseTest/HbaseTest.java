package org.zzr1000.hbaseTest;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class HbaseTest {

    private static Configuration configuration;
    private static Connection hbaseConnection;

    static {//两种方式：一种是指定这三个需要的参数 ; 二是使用hbase-site.xml文件 ; 第一种情况下,参数可以直接指定,也可以通过配置文件获得
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "114.67.69.229");
        configuration.set("zookeeper.znode.parent", "/hbase");
    }

    public static void main(String[] args) throws Exception{
        //createTable("t0614","cf1","cf2");
        //insertData("t0614","r1","cf1","c1","v1");
        //createTablePreSplit1("t0614s1","1","9",4,"cf1");
        //System.out.println(createTablePreSplit2("t0615", Arrays.asList("cf1")));
        //putTest("t0615");批量插入，测试预分区表t0615
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

    //预分区方法1：输入startkey、endkey、region数,自动创建预分区
    public static void createTablePreSplit1(
            String tableName,
            String startKey,
            String endKey,
            int numRegions,
            String... cf) throws Exception {
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
            byte[][] splits =getHexSplits(//得到的是一个字节数组的数组
                    startKey,
                    endKey,
                    numRegions);
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

    //预分区方法2：输入固定的预分区数组
    public static boolean createTablePreSplit2(
            String tableName,
            List<String> columnFamily) throws IOException {
        try {
            if (StringUtils.isBlank(tableName)
                    || columnFamily == null
                    || columnFamily.size() < 0) {
                System.out.println("===Parameters tableName|columnFamily " +
                        "should not be null,Please check!===");
            }

            getConnection();
            HBaseAdmin hBaseAdmin =(HBaseAdmin) hbaseConnection.getAdmin();

            if (hBaseAdmin.tableExists(tableName)) {
                return true;
            } else {
                HTableDescriptor tableDescriptor = new HTableDescriptor(
                        TableName.valueOf(tableName));

                for (String cf : columnFamily) {
                    tableDescriptor.addFamily(new HColumnDescriptor(cf));
                }

                byte[][] splitKeys = getSplitKeys();
                hBaseAdmin.createTable(tableDescriptor,splitKeys);//指定splitkeys
                System.out.println("===Create Table " + tableName
                        + " Success! columnFamily:" + columnFamily.toString()
                        + "===");
            }
        } catch (MasterNotRunningException e) {
            return false;
        } catch (ZooKeeperConnectionException e) {
            return false;
        } catch (IOException e) {
            return false;
        }finally {
            closeConnection();
        }

        return true;
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

    public static void insertData2(String tableName,int num) throws IOException {
        //基础数据准备：两个cf ; 插入的每条记录，只是rowkey不同,值都是相同的,都是如下固定值
        String[] columns = {"userInfo", "companyInfo"};
        Map<String, List<String>> familierMap = new HashMap<>();

        List<String> userInfoList = new ArrayList<>();
        userInfoList.add("name-zhang");
        userInfoList.add("sexy-man");
        userInfoList.add("age-18");
        userInfoList.add("Timestamp-" + System.currentTimeMillis());
        familierMap.put("userInfo", userInfoList);

        List<String> companyInfoList = new ArrayList<>();
        companyInfoList.add("address-Nanjign");
        companyInfoList.add("companyCode-hhhh");
        companyInfoList.add("tel-66666");
        companyInfoList.add("Timestamp-" + System.currentTimeMillis());
        familierMap.put("companyInfo", companyInfoList);


        System.out.println(">>>>>> 开始测试insert功能");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            String rowKey = getRandomString2(50);
            for (String family : columns) {
                List<String> familiers = familierMap.get(family);
                for (String f : familiers) {
                    insertData("testTable",rowKey, family, f.split("-")[0], f.split("-")[1]);
                }
            }
        }
        System.out.println("插入耗时：" + (System.currentTimeMillis() - startTime) / 1000 + " s" + " total:" + num);

    }
  //@Test
  //得到两位随机数
    public static String getRandomNumber() {
        String ranStr = Math.random() + "";
      //System.out.println(ranStr);
        int pointIndex = ranStr.indexOf(".");
      //System.out.println(ranStr.substring(pointIndex + 1, pointIndex + 3));
        return ranStr.substring(pointIndex + 1, pointIndex + 3);
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
            byte[] rowkey = Bytes.toBytes(getRandomNumber() + "-" + System.currentTimeMillis() + "-" + i);
            Put put = new Put(rowkey);
            put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("name"), Bytes.toBytes("zs" + i));
            list.add(put);
        }
        return list;
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

    private static byte[][] getSplitKeys() {
        String[] keys = new String[] {
                "10|", "20|", "30|",
                "40|", "50|", "60|",
                "70|", "80|", "90|" };
        byte[][] splitKeys = new byte[keys.length][];
        TreeSet<byte[]> rows = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);//升序排序
        for (int i = 0; i < keys.length; i++) {
            rows.add(Bytes.toBytes(keys[i]));
        }

        Iterator<byte[]> rowKeyIter = rows.iterator();
        int i=0;
        while (rowKeyIter.hasNext()) {
            byte[] tempRow = rowKeyIter.next();
            rowKeyIter.remove();
            splitKeys[i] = tempRow;
            i++;
        }
        return splitKeys;
    }

    @Test
    public void functionTest(){
        //System.out.println(new BigInteger("1111"));//默认10进制
        //System.out.println(new BigInteger("f",16));//radix:进制
        System.out.println(String.format("%016x", 200));//%x：转换为16进制;016:添0补齐16位//重要
        System.out.println(String.format("%016x", 200).getBytes());
    }

    @Test
    public void getHexSplitsTest() {
        byte[][] splits = new byte[10-1][];
        BigInteger lowestKey = new BigInteger("0000000000", 16);
        System.out.println(lowestKey);
        BigInteger highestKey = new BigInteger("fffffffff", 16);
        System.out.println(highestKey);
        BigInteger range = highestKey.subtract(lowestKey);
        System.out.println(range);
        BigInteger regionIncrement = range.divide(BigInteger.valueOf(16));
        System.out.println(regionIncrement);
        lowestKey = lowestKey.add(regionIncrement);
        System.out.println(lowestKey);
        for(int i=0; i < 10-1;i++) {
            BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
            byte[] b = String.format("%016x", key).getBytes();
            splits[i] = b;
        }

        for(byte[] i:splits) {//最终提供的startKey、endKey都不会在结果中，只打印中间结果
            System.out.println(new String(i));
        }
    }

    public static String getRandomString2(int length){
        //产生随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //循环length次
        for(int i=0; i<length; i++){
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result=Math.round(Math.random()*25+65);
                    //将ASCII码转换成字符
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(String.valueOf
                            (new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

}
