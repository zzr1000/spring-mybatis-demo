package org.zzr1000.prestoHbaseTest.frame;

import com.facebook.presto.spi.ConnectorHandleResolver;
import com.facebook.presto.spi.connector.Connector;
import com.facebook.presto.spi.connector.ConnectorContext;
import com.facebook.presto.spi.connector.ConnectorFactory;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.json.JsonModule;
import io.airlift.log.Logger;

import java.util.Map;
import java.util.Objects;

import static org.zzr1000.prestoHbaseTest.util.Constant.CONNECTOR_NAME;

public class HBaseConnectorFactory implements ConnectorFactory {

    private static final Logger log = Logger.get(HBaseConnectorFactory.class);

    @Override
    public String getName() {
        return CONNECTOR_NAME;
    }

    @Override
    public ConnectorHandleResolver getHandleResolver() {
        return new HBaseHandleResolver();
    }

    @Override
    public Connector create(String connectorId, Map requiredConfig, ConnectorContext context) {
        Objects.requireNonNull(requiredConfig, "requiredConfig is null");
        try {
            Bootstrap e = new Bootstrap(
                    new Module[]{new JsonModule(),
                    new HBaseModule(connectorId, context.getTypeManager())});

            Injector injector = e.strictConfig()
                    .doNotInitializeLogging()
                    .setRequiredConfigurationProperties(requiredConfig)
                    .initialize();

            return injector.getInstance(HBaseConnector.class);

        } catch (Exception e) {
            log.error(e, e.getMessage());
            return null;
        }
    }
}