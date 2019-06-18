package org.zzr1000.prestoHbaseTest.frame;

import com.facebook.presto.spi.*;
import com.facebook.presto.spi.connector.ConnectorMetadata;
import com.google.inject.Inject;
import org.zzr1000.prestoHbaseTest.connection.HBaseClientManager;
import org.zzr1000.prestoHbaseTest.meta.HBaseTables;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class HBaseMetadata implements ConnectorMetadata {

    private final HBaseConnectorId connectorId;
    private final HBaseTables hbaseTables;
    private final HBaseClientManager hbaseClientManager;

    @Inject
    public HBaseMetadata(HBaseConnectorId connectorId, HBaseTables hbaseTables, HBaseClientManager hbaseClientManager) {
        this.connectorId = connectorId;
        this.hbaseTables = requireNonNull(hbaseTables, "hbaseTables is null");
        this.hbaseClientManager = hbaseClientManager;
    }

    @Override
    public List<String> listSchemaNames(ConnectorSession session) {
        return null;
    }

    @Override
    public ConnectorTableHandle getTableHandle(ConnectorSession session, SchemaTableName tableName) {
        return null;
    }

    @Override
    public List<ConnectorTableLayoutResult> getTableLayouts(ConnectorSession session, ConnectorTableHandle table, Constraint<ColumnHandle> constraint, Optional<Set<ColumnHandle>> desiredColumns) {
        return null;
    }

    @Override
    public ConnectorTableLayout getTableLayout(ConnectorSession session, ConnectorTableLayoutHandle handle) {
        return null;
    }

    @Override
    public ConnectorTableMetadata getTableMetadata(ConnectorSession session, ConnectorTableHandle table) {
        return null;
    }

    @Override
    public List<SchemaTableName> listTables(ConnectorSession session, String schemaNameOrNull) {
        return null;
    }

    @Override
    public Map<String, ColumnHandle> getColumnHandles(ConnectorSession session, ConnectorTableHandle tableHandle) {
        return null;
    }

    @Override
    public ColumnMetadata getColumnMetadata(ConnectorSession session, ConnectorTableHandle tableHandle, ColumnHandle columnHandle) {
        return null;
    }

    @Override
    public Map<SchemaTableName, List<ColumnMetadata>> listTableColumns(ConnectorSession session, SchemaTablePrefix prefix) {
        return null;
    }
}
