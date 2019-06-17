package org.zzr1000.prestoHbaseTest.meta;

import com.facebook.presto.spi.ConnectorTableHandle;
import com.facebook.presto.spi.SchemaTableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class HBaseTableHandle implements ConnectorTableHandle {

    private final SchemaTableName schemaTableName;

    @JsonCreator//没有无参构造函数,需要增加该注解,防止发序列化报错
    public HBaseTableHandle(@JsonProperty("schemaTableName")
                            SchemaTableName schemaTableName) {
        this.schemaTableName = requireNonNull(schemaTableName, "schemaTableName is null");
    }

    @JsonProperty
    public SchemaTableName getSchemaTableName() {
        return schemaTableName;
    }


    //=============重写三个基础方法
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HBaseTableHandle that = (HBaseTableHandle) o;
        return Objects.equals(schemaTableName, that.schemaTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaTableName);
    }

    @Override
    public String toString() {
        return "HBaseTableHandle{" +
                "schemaTableName=" + schemaTableName +
                '}';
    }

}
