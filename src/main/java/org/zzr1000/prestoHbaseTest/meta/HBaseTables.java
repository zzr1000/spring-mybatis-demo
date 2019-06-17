package org.zzr1000.prestoHbaseTest.meta;

import com.facebook.presto.spi.SchemaTableName;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import io.airlift.log.Logger;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.zzr1000.prestoHbaseTest.connection.HBaseClientManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HBaseTables {

    public static final Logger logger = Logger.get(HBaseTables.class);
    private HBaseClientManager hbaseClientManager;

    @Inject
    public HBaseTables(HBaseClientManager hbaseClientManager) {
        this.hbaseClientManager = hbaseClientManager;
    }

    //1、getSchemaNames
    public Set<String> getSchemaNames() {
        NamespaceDescriptor[] namespaceDescriptors = new NamespaceDescriptor[0];
        Admin admin = null;
        try {
            admin = hbaseClientManager.getAdmin();
            namespaceDescriptors = admin.listNamespaceDescriptors();
        } catch (IOException e) {
            logger.error(e, e.getMessage());
        } finally {
            if (admin != null) {
                hbaseClientManager.close(admin);
            }
        }

        HashSet<String> set = new HashSet<>();
        NamespaceDescriptor[] temp = namespaceDescriptors;
        int namespaceDescriptorLength = namespaceDescriptors.length;

        for (int i = 0; i < namespaceDescriptorLength; ++i) {
            NamespaceDescriptor namespaceDescriptor = temp[i];
            set.add(namespaceDescriptor.getName());
        }

        return set;
    }

    //2、getTables
    public Map<SchemaTableName, HBaseTableHandle> getTables(Admin admin, String schema) {
        Map<SchemaTableName, HBaseTableHandle> tables;
        try {
            ImmutableMap.Builder<SchemaTableName, HBaseTableHandle> tablesBuilder = ImmutableMap.builder();
            HTableDescriptor[] descriptors = admin.listTableDescriptorsByNamespace(schema);
            for (HTableDescriptor table : descriptors) {
                // If the target table is in the other namespace, table.getNameAsString() will return
                // value like 'namespace1:tableName1', so we have to remove the unnecessary namespace.
                String tableName;
                if (table.getNameAsString() != null && table.getNameAsString().contains(":")) {
                    tableName = table.getNameAsString().split(":")[1];
                } else {
                    tableName = table.getNameAsString();
                }
                SchemaTableName schemaTableName = new SchemaTableName(schema, tableName);

                tablesBuilder.put(schemaTableName, new HBaseTableHandle(schemaTableName));
            }
            tables = tablesBuilder.build();
            return tables;
        } catch (Exception ex) {
            logger.error(ex, ex.getMessage());
        }
        return null;
    }


}
