package org.zzr1000.prestoHbaseTest.frame;

import com.facebook.presto.spi.type.TypeManager;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.airlift.configuration.ConfigBinder;
import org.zzr1000.prestoHbaseTest.connection.HBaseClientManager;
import org.zzr1000.prestoHbaseTest.meta.HBaseConfig;
import org.zzr1000.prestoHbaseTest.meta.HBaseTables;
import org.zzr1000.prestoHbaseTest.query.HBaseRecordSetProvider;
import org.zzr1000.prestoHbaseTest.schedule.HBaseSplitManager;

import java.util.Objects;

class HBaseModule implements Module {

    private final String connectorId;
    private final TypeManager typeManager;

    HBaseModule(String connectorId, TypeManager typeManager) {
        this.connectorId = Objects.requireNonNull(connectorId, "connector id is null");
        this.typeManager = Objects.requireNonNull(typeManager, "typeManager is null");
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(TypeManager.class).toInstance(this.typeManager);
        binder.bind(HBaseConnector.class).in(Scopes.SINGLETON);
        binder.bind(HBaseConnectorId.class).toInstance(new HBaseConnectorId(this.connectorId));
        binder.bind(HBaseMetadata.class).in(Scopes.SINGLETON);
        binder.bind(HBaseClientManager.class).in(Scopes.SINGLETON);
        binder.bind(HBaseSplitManager.class).in(Scopes.SINGLETON);
        binder.bind(HBaseRecordSetProvider.class).in(Scopes.SINGLETON);
        binder.bind(HBaseTables.class).in(Scopes.SINGLETON);
        ConfigBinder.configBinder(binder).bindConfig(HBaseConfig.class);
    }

}
