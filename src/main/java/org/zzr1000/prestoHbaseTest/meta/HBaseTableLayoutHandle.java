package org.zzr1000.prestoHbaseTest.meta;

import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ConnectorTableLayoutHandle;
import com.facebook.presto.spi.predicate.TupleDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

public class HBaseTableLayoutHandle implements ConnectorTableLayoutHandle {



    private final HBaseTableHandle table;

    private final TupleDomain<ColumnHandle> constraint;

    @JsonCreator
    public HBaseTableLayoutHandle(
            @JsonProperty("table") HBaseTableHandle table,
            @JsonProperty("constraint") TupleDomain<ColumnHandle> constraint) {
        this.table = requireNonNull(table, "table is null");
        this.constraint = requireNonNull(constraint, "constraint is null");
    }

    @JsonProperty
    public HBaseTableHandle getTable() {
        return table;
    }

    @JsonProperty
    public TupleDomain<ColumnHandle> getConstraint() {
        return constraint;
    }

    @Override
    public String toString() {
        return table.toString();
    }

}
