package org.zzr1000.prestoHbaseTest.meta;

import io.airlift.configuration.Config;
import io.airlift.log.Logger;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.util.Arrays;

public class HBaseConfig {

    private static final Logger log = Logger.get(HBaseConfig.class);

    private String hbaseZookeeperQuorum;
    private String zookeeperClientPort;
    private String hbaseIsDistributed;
    private String prestoWorkersIp;
    private String prestoWorkersName;
    private int prestoServerPort;
    private String zookeeperZnodeParent;
    private boolean randomScheduleRedundantSplit;
    private String metaDir;

    //get、set 方法
    @NotNull
    public String getMetaDir() {
        return metaDir;
    }

    @Config("meta-dir")
    public void setMetaDir(String metaDir) {
        this.metaDir = metaDir;
    }

    public String getPrestoWorkersName() {
        return prestoWorkersName;
    }

    @Config("presto-workers-name")
    public void setPrestoWorkersName(String prestoWorkersName) {
        this.prestoWorkersName = prestoWorkersName;
    }

    public boolean isRandomScheduleRedundantSplit() {
        return randomScheduleRedundantSplit;
    }

    @Config("random-schedule-redundant-split")
    public void setRandomScheduleRedundantSplit(boolean randomScheduleRedundantSplit) {
        this.randomScheduleRedundantSplit = randomScheduleRedundantSplit;
    }


    @Config("zookeeper-quorum")
    public HBaseConfig setHbaseZookeeperQuorum(String hbaseZookeeperQuorum) {
        this.hbaseZookeeperQuorum = hbaseZookeeperQuorum;
        return this;
    }

    @Config("zookeeper-client-port")
    public HBaseConfig setZookeeperClientPort(String zookeeperClientPort) {
        this.zookeeperClientPort = zookeeperClientPort;
        return this;
    }

    @Config("hbase-cluster-distributed")
    public void setHbaseIsDistributed(String hbaseIsDistributed) {
        this.hbaseIsDistributed = hbaseIsDistributed;
    }

    @Config("presto-server-port")
    public void setPrestoServerPort(int prestoServerPort) {
        this.prestoServerPort = prestoServerPort;
    }

    @Config("presto-workers-ip")
    public void setPrestoWorkersIp(String prestoWorkersIp) {
        this.prestoWorkersIp = prestoWorkersIp;
    }

    @Config("zookeeper-znode-parent")
    public void setZookeeperZnodeParent(String zookeeperZnodeParent) {
        this.zookeeperZnodeParent = zookeeperZnodeParent;
    }

    @NotNull
    public String getHbaseZookeeperQuorum() {
        return this.hbaseZookeeperQuorum;
    }

    @NotNull
    public String getZookeeperClientPort() {
        return this.zookeeperClientPort;
    }

    @NotNull
    public String getZookeeperZnodeParent() {
        return this.zookeeperZnodeParent;
    }

    public String getHbaseIsDistributed() {
        return this.hbaseIsDistributed;
    }

    public String getPrestoWorkersIp() {
        return prestoWorkersIp;
    }

    @NotNull
    public int getPrestoServerPort() {
        return prestoServerPort;
    }


    //init方法：
    public void init() {
        // If we don't support to schedule a split to a specify worker,
        // then prestoWorkersName and prestoWorkersIp can be null
        if (this.randomScheduleRedundantSplit) {
            String[] workersIp = Arrays.stream(this.prestoWorkersName.split(",")).map(hostname -> {
                InetAddress address;
                try {
                    address = InetAddress.getByName(hostname);
                    return address.getHostAddress();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return "";
                }
            }).toArray(String[]::new);

            this.prestoWorkersIp = StringUtils.join(workersIp, ",");
        }
    }

    //重写方法
    @Override
    public String toString() {
        return "HBaseConfig{" +
                "hbaseZookeeperQuorum='" + hbaseZookeeperQuorum + '\'' +
                ", zookeeperClientPort='" + zookeeperClientPort + '\'' +
                ", hbaseIsDistributed='" + hbaseIsDistributed + '\'' +
                ", prestoWorkersIp='" + prestoWorkersIp + '\'' +
                ", prestoWorkersName='" + prestoWorkersName + '\'' +
                ", prestoServerPort=" + prestoServerPort +
                ", randomScheduleRedundantSplit=" + randomScheduleRedundantSplit +
                ", metaDir='" + metaDir + '\'' +
                '}';
    }


}
