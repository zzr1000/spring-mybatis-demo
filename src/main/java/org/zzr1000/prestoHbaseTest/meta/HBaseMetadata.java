package org.zzr1000.prestoHbaseTest.meta;

import com.facebook.presto.spi.*;
import com.facebook.presto.spi.connector.ConnectorMetadata;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.apache.hadoop.hbase.client.Admin;
import org.zzr1000.prestoHbaseTest.connection.HBaseClientManager;
import org.zzr1000.prestoHbaseTest.frame.HBaseConnectorId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.zzr1000.prestoHbaseTest.util.Types.checkType;

public class HBaseMetadata implements ConnectorMetadata {

    //1、
    @Override
    public List<String> listSchemaNames(ConnectorSession session) {
        return listSchemaNames();
    }

    //2、
    @Override
    public ConnectorTableHandle getTableHandle(ConnectorSession session, SchemaTableName tableName) {
        requireNonNull(tableName, "tableName is null");
        Admin admin = null;
        ConnectorTableHandle connectorTableHandle;
        try {
            admin = hbaseClientManager.getAdmin();
            connectorTableHandle = hbaseTables.getTables(
                    admin, tableName.getSchemaName()).get(tableName);
        } finally {
            if (admin != null) {
                hbaseClientManager.close(admin);
            }
        }
        return connectorTableHandle;
    }

    //3、
    @Override
    public List<ConnectorTableLayoutResult> getTableLayouts(ConnectorSession session,
                                                            ConnectorTableHandle table,
                                                            Constraint<ColumnHandle> constraint,
                                                            Optional<Set<ColumnHandle>> desiredColumns) {
        HBaseTableHandle tableHandle = checkType(table, HBaseTableHandle.class, "tableHandle");
        ConnectorTableLayout layout = new ConnectorTableLayout(
                new HBaseTableLayoutHandle(tableHandle, constraint.getSummary()));
        return ImmutableList.of(new ConnectorTableLayoutResult(layout, constraint.getSummary()));

    }

    //4、
    @Override
    public ConnectorTableLayout getTableLayout(ConnectorSession session,
                                               ConnectorTableLayoutHandle handle) {
        HBaseTableLayoutHandle layout = checkType(handle, HBaseTableLayoutHandle.class, "layout");
        return new ConnectorTableLayout(layout);
    }

    //5、
    @Override
    public ConnectorTableMetadata getTableMetadata(ConnectorSession session,
                                                   ConnectorTableHandle table) {
        HBaseTableHandle hBaseTableHandle = (HBaseTableHandle) table;
        SchemaTableName tableName = new SchemaTableName(hBaseTableHandle.getSchemaTableName().getSchemaName(),
                hBaseTableHandle.getSchemaTableName().getTableName());
        return this.getTableMetadata(tableName);
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

    //==================待类内部引用的方法

    private final HBaseTables hbaseTables ; //加了final关键字，就必须初始化，或者不除初始化，加入到构造方法中，也可以
    private final HBaseConnectorId connectorId;
    private final HBaseClientManager hbaseClientManager;

    @Inject //作用：上述变量，都没有初始化，就被用到构造函数中，被赋值给其他对应：这就是作用，不需要使用new实例化了：. .
    public HBaseMetadata(HBaseConnectorId connectorId, HBaseTables hbaseTables, HBaseClientManager hbaseClientManager) {
        this.connectorId = connectorId;
        this.hbaseTables = requireNonNull(hbaseTables, "hbaseTables is null");
        this.hbaseClientManager = hbaseClientManager;
    }

    private List<String> listSchemaNames() {
        return ImmutableList.copyOf(hbaseTables.getSchemaNames());
    }

    private ConnectorTableMetadata getTableMetadata(SchemaTableName tableName) {
        if (!this.listSchemaNames().contains(tableName.getSchemaName())) {
            return null;
        } else {
            HBaseTable table = hbaseClientManager.getTable(tableName.getSchemaName(), tableName.getTableName());
            return table == null ? null : new ConnectorTableMetadata(tableName, table.getColumnsMetadata());
        }
    }


}
