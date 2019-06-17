package org.zzr1000.prestoHbaseTest.frame;

import java.util.Objects;

public final class HBaseConnectorId {

    private final String id;

    public HBaseConnectorId(String id) {
        this.id = Objects.requireNonNull(id, "id is null");
    }

    public String getId() {
        return this.id;
    }
    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            HBaseConnectorId other = (HBaseConnectorId) obj;
            return Objects.equals(this.id, other.id);
        } else {
            return false;
        }
    }

}
