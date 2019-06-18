package org.zzr1000.prestoHbaseTest.schedule;

import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.HostAddress;

import java.util.List;

public class HBaseSplit implements ConnectorSplit {
    @Override
    public boolean isRemotelyAccessible() {
        return false;
    }

    @Override
    public List<HostAddress> getAddresses() {
        return null;
    }

    @Override
    public Object getInfo() {
        return null;
    }
}
