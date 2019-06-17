package org.zzr1000.prestoHbaseTest.meta;

import com.facebook.presto.spi.ColumnMetadata;
import com.google.common.collect.ImmutableList;
import io.airlift.log.Logger;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.zzr1000.prestoHbaseTest.util.Utils;

import java.util.List;
import java.util.Objects;

public class HBaseTable {

    public static final Logger logger = Logger.get(HBaseTable.class);

    private final HTableDescriptor hTableDescriptor;
    private final String schemaName;
    private final List<ColumnMetadata> columnsMetadata;

    public HBaseTable(String schemaName, HTableDescriptor tabDesc, HBaseConfig config) {
        this.hTableDescriptor = Objects.requireNonNull(tabDesc, "tabDesc is null");
        this.schemaName = Objects.requireNonNull(schemaName, "schemaName is null");
        ImmutableList<ColumnMetadata> tableMeta = null;
        try {
            String tableName = tabDesc.getNameAsString() != null && tabDesc.getNameAsString().contains(":") ?
                    tabDesc.getNameAsString().split(":")[1] : tabDesc.getNameAsString();
            tableMeta = Utils.getColumnMetaFromJson(schemaName, tableName, config.getMetaDir());
            if (tableMeta == null || tableMeta.size() <= 0) {
                logger.error("OOPS! Table meta info cannot be NULL, table name=" + tabDesc.getNameAsString());
                throw new Exception("Cannot find meta info of table " + tabDesc.getNameAsString() + ".");
            }
        } catch (Exception e) {
            logger.error(e, e.getMessage());
        }
        this.columnsMetadata = tableMeta;
    }

    public String getTableName() {
        return this.hTableDescriptor.getNameAsString();
    }

    public String getTableSchemaName() {
        return this.schemaName;
    }

    public List<ColumnMetadata> getColumnsMetadata() {
        return this.columnsMetadata;
    }

    public String getRegions() {
        return this.hTableDescriptor.getRegionSplitPolicyClassName();
    }

}
