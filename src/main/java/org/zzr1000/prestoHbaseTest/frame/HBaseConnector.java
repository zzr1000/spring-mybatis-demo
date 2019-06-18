package org.zzr1000.prestoHbaseTest.frame;

import com.facebook.presto.spi.connector.Connector;
import com.facebook.presto.spi.connector.ConnectorMetadata;
import com.facebook.presto.spi.connector.ConnectorSplitManager;
import com.facebook.presto.spi.connector.ConnectorTransactionHandle;
import com.facebook.presto.spi.transaction.IsolationLevel;
import io.airlift.bootstrap.LifeCycleManager;
import io.airlift.log.Logger;
import org.zzr1000.prestoHbaseTest.query.HBaseRecordSetProvider;
import org.zzr1000.prestoHbaseTest.schedule.HBaseSplitManager;

import javax.inject.Inject;
import java.util.Objects;

public class HBaseConnector implements Connector {

    private static final Logger log = Logger.get(HBaseConnector.class);
    private final LifeCycleManager lifeCycleManager;
    private final HBaseMetadata metadata;
    private final HBaseSplitManager splitManager;
    private final HBaseRecordSetProvider recordSetProvider;

    @Inject
    public HBaseConnector(LifeCycleManager lifeCycleManager,
                          HBaseMetadata metadata,
                          HBaseSplitManager splitManager,
                          HBaseRecordSetProvider recordSetProvider) {
        this.lifeCycleManager = Objects.requireNonNull(lifeCycleManager, "lifeCycleManager is null");
        this.metadata = Objects.requireNonNull(metadata, "metadata is null");
        this.splitManager = Objects.requireNonNull(splitManager, "splitManager is null");
        this.recordSetProvider = Objects.requireNonNull(recordSetProvider, "recordSetProvider is null");
    }

    @Override
    public ConnectorTransactionHandle beginTransaction(IsolationLevel isolationLevel, boolean readOnly) {
        return null;
    }

    @Override
    public ConnectorMetadata getMetadata(ConnectorTransactionHandle transactionHandle) {
        return null;
    }

    @Override
    public ConnectorSplitManager getSplitManager() {
        return null;
    }
}
