package org.zzr1000.prestoHbaseTest.meta;

import com.google.inject.Inject;
import io.airlift.log.Logger;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.zzr1000.prestoHbaseTest.connection.HBaseClientManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HBaseTables {

    public static final Logger logger = Logger.get(HBaseTables.class);
    private HBaseClientManager hbaseClientManager;

    @Inject
    public HBaseTables(HBaseClientManager hbaseClientManager) {
        this.hbaseClientManager = hbaseClientManager;
    }

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



}
