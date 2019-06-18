package org.zzr1000.prestoHbaseTest.frame;

import com.facebook.presto.spi.ConnectorHandleResolver;
import org.zzr1000.prestoHbaseTest.meta.HBaseColumnHandle;
import org.zzr1000.prestoHbaseTest.meta.HBaseTableHandle;
import org.zzr1000.prestoHbaseTest.meta.HBaseTableLayoutHandle;
import org.zzr1000.prestoHbaseTest.schedule.HBaseSplit;

public class HBaseHandleResolver implements ConnectorHandleResolver {

    @Override
    public Class getTableLayoutHandleClass() {
        return HBaseTableLayoutHandle.class;
    }

    @Override
    public Class getTableHandleClass() {
        return HBaseTableHandle.class;
    }

    @Override
    public Class getColumnHandleClass() {
        return HBaseColumnHandle.class;
    }

    @Override
    public Class getSplitClass() {
        return HBaseSplit.class;
    }

    @Override
    public Class getTransactionHandleClass() {
        return HBaseTransactionHandle.class;
    }

}
