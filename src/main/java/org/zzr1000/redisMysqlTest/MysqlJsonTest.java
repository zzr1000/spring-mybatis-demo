package org.zzr1000.redisMysqlTest;

//refer to :
// https://blog.csdn.net/xiao__gui/article/details/8612503

/*
实现很简单:
就是把查询结果ResultSet的每一条数据转换成一个json对象，
数据中的每一列的列名和值组成键值对，放在对象中，
最后把对象组织成一个json数组。
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MysqlJsonTest {

    public String resultSetToJson(ResultSet rs) throws SQLException {
        JSONArray array = new JSONArray();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()){
            JSONObject jsonObject = new JSONObject();

            for(int i = 1; i <=columnCount ; i++){
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObject.put(columnName,value);
            }
            array.put(jsonObject);
        }
        return array.toString();
    }
}
