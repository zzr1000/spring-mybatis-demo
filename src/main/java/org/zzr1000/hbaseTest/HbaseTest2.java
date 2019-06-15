package org.zzr1000.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.junit.Test;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;

import java.io.IOException;
import java.nio.ByteBuffer;

public class HbaseTest2 {

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


    public static void main(String[] args) {

    }

    @Test
    public void hashTest(){
        long currentId = 1L;
        byte[] rowkey = Bytes.add(//对rowkey进行散列的方法
                MD5Hash.getMD5AsHex(Bytes.toBytes(currentId))
                        .substring(0, 8)
                        .getBytes(),
                Bytes.toBytes(currentId));//在加上自己的原有的rowkey:防止不同rowkey出现相同散列值:..

        //输出散列之后的值
        for(byte b : rowkey) {
            System.out.println(String.format("%d", b));
        }

        String result = "";
        for(byte b : rowkey) {
            result = result + String.format("%d", b);
        }

        System.out.println(result);
    }

    @Test
    public void hashTest2() {
        int id = 16;
        byte[] rkid = Bytes.toBytes(id);
        String prefix = MD5Hash.getMD5AsHex(rkid).substring(0, 3); //取出MD5码的前三位
        byte[] rkid2 = Bytes.toBytes(prefix);
        byte[] rowkey = Bytes.add(rkid, rkid2); //合并
    }

    @Test
    public void md5Test() {
        System.out.println(MD5Hash.getMD5AsHex(Bytes.toBytes("506573390")));//5a842009c4fc414cb66de263e6a3330c
    }

    @Test
    public void hashTest2Detail() {
        int id = 16;
        byte[] rkid = Bytes.toBytes(id);
        for(byte b : rkid) {
            System.out.println(b);//0、0、0、16
        }
        String prefix = MD5Hash.getMD5AsHex(rkid).substring(0, 3); //取出MD5码的前三位
        System.out.println(MD5Hash.getMD5AsHex(rkid));
        System.out.println(MD5Hash.getMD5AsHex(rkid).substring(0, 3));
        byte[] rkid2 = Bytes.toBytes(prefix);//a43
        for(byte b : rkid2) {
            System.out.println(b);//97、52、51
        }
        byte[] rowkey = Bytes.add(rkid, rkid2); //合并
        for(byte b : rowkey) {
            System.out.println(b);//0、0、0、16、97、52、51
        }
    }

    @Test
    public void hashTest3Detail() {//important
        byte a[] = Bytes.toBytes("hello");
        byte b[] = Bytes.toBytes("maizi");

     //多个字节，拼装成一个row key
        byte c[] =Bytes.add(a,b);
        System.out.println(Bytes.toString(c));

        byte d[] = Bytes.head(c, 5);
        System.out.println(Bytes.toString(d));

        byte e[] = Bytes.tail(c, 3);
        System.out.println(Bytes.toString(e));

    }

    @Test
    public void hbaseByteTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putInt(1234);
        byteBuffer.putInt(5678);
        byte[] rowkey = byteBuffer.array();
        for (byte b: rowkey){
          //System.out.println(b);
        }
        String sb = Bytes.toStringBinary(rowkey);
        String s = rowkey.toString();
        rowkey = s.getBytes();
        for (byte b: rowkey){
          //System.out.println(b);
        }
      //System.out.println(sb);
        rowkey = "test".getBytes();
        System.out.println(Bytes.toString(rowkey));
        System.out.println(Bytes.toStringBinary(rowkey));
    }

    @Test
    public void partitionTest(){



    }

}



