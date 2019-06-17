package org.zzr1000.prestoHbaseTest.util;

import com.facebook.presto.spi.ColumnMetadata;
import com.google.common.collect.ImmutableList;
import io.airlift.log.Logger;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.zzr1000.prestoHbaseTest.meta.HBaseColumnMetadata;

import java.io.File;

import static org.zzr1000.prestoHbaseTest.util.Constant.*;

public class Utils {

    public static final Logger logger = Logger.get(Utils.class);

    /**
     * Fetch column meta info from json file
     *
     * @param schemaName schema name
     * @param tableName  table name
     * @param metaDir    meta dir
     * @return list of column meta info
     */
    public static ImmutableList<ColumnMetadata> getColumnMetaFromJson(String schemaName, String tableName, String metaDir) {
        long startTime = System.currentTimeMillis();
        ImmutableList.Builder<ColumnMetadata> columnsMetadata = ImmutableList.builder();
        try {
            String jsonStr = readTableJson(schemaName, tableName, metaDir);
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray cols = obj.getJSONArray(JSON_TABLEMETA_COLUMNES);
            for (int i = 0; i < cols.length(); i++) {
                JSONObject temp = new JSONObject(cols.getString(i));
                String family = temp.getString(JSON_TABLEMETA_FAMILY);
                String columnName = temp.getString(JSON_TABLEMETA_COLUMNNAME);
                String type = temp.getString(JSON_TABLEMETA_TYPE);
                columnsMetadata.add(new HBaseColumnMetadata(family, columnName, matchType(type)));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.info(String.format("Read COLUMN meta info of TABLE %s.%s from json, totally used %d ms.",
                    schemaName, tableName, (System.currentTimeMillis() - startTime)));
        }
        return columnsMetadata.build();
    }



    //=======被使用的function

    /**
     * Read table json from metaDir by schema name and table name
     *
     * @param schemaName schema name
     * @param tableName  table name
     * @param metaDir    meta dir
     * @return json file content
     */
    private static String readTableJson(String schemaName, String tableName, String metaDir) {
        try {
            String tableMetaPath = metaDir + File.separator
                    + (schemaName == null || "".equals(schemaName) ? DEFAULT_HBASE_NAMESPACE_NAME : schemaName)
                    + File.separator + tableName + TABLE_META_FILE_TAIL;
            return FileUtils.readFileToString(new File(tableMetaPath), JSON_ENCODING_UTF8);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

}
