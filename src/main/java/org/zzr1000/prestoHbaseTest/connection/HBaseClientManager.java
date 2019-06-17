package org.zzr1000.prestoHbaseTest.connection;

import com.google.inject.Inject;
import io.airlift.log.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.zzr1000.prestoHbaseTest.meta.HBaseConfig;

import static org.zzr1000.prestoHbaseTest.util.Constant.SYSTEMOUT_INTERVAL;

public class HBaseClientManager {
    private static final Logger log = Logger.get(HBaseClientManager.class);

    private Connection connection;
    private HBaseConfig config;

    @Inject
    public HBaseClientManager(HBaseConfig config) {
        this.config = config;
        this.config.init();
        this.connection = createConnection();
    }

    public Admin getAdmin() {
        try {
            if (connection == null) {
                connection = createConnection();
            }
            return connection.getAdmin();
        } catch (Exception ex) {
            log.error(ex, ex.getMessage());
        }
        return null;
    }

    public void close(Admin admin) {
        try {
            admin.close();
        } catch (Exception ex) {
            log.error(ex, ex.getMessage());
        }
    }


    //=============类中被引用方法：
    public Connection createConnection() {
        Configuration conf;
        Connection connection;
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", config.getHbaseZookeeperQuorum());
            conf.set("hbase.zookeeper.property.clientPort", config.getZookeeperClientPort());
            conf.set("hbase.client.ipc.pool.size", "1");
            //  RPC fail retry times
            conf.set("hbase.client.retries.number", "3");

            //conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("zookeeper.znode.parent", config.getZookeeperZnodeParent());

            // set this param a bigger value to avoid SocketTimeoutException when you invoke scanner.next()
            conf.set("hbase.client.scanner.timeout.period", "90000");

            if (config.getHbaseIsDistributed() != null) {
                conf.set("hbase.cluster.distributed", config.getHbaseIsDistributed());
            }
            long startTime = System.currentTimeMillis();
            connection = ConnectionFactory.createConnection(conf);

            if (System.currentTimeMillis() % SYSTEMOUT_INTERVAL == 0) {
                log.info("Create HBase connection " + (connection == null ? "succeed." : "failed.")
                        + ", used " + (System.currentTimeMillis() - startTime) + " mill sec");
            }

            return connection;
        } catch (Exception ex) {
            log.error(ex, ex.getMessage());
            return null;
        }
    }


}
