package org.zzr1000.prestoHbaseTest.util;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

public class Types {

    private Types() {
    }

    public static <A, B extends A> B checkType(A value, Class<B> target, String name) {

        if (value == null) {
            throw new NullPointerException(format("%s is null", name));
        }

        //使用guava的checkArgument
        checkArgument(target.isInstance(value),
                "%s must be of type %s, not %s",
                name,
                target.getName(),
                value.getClass().getName());

        return target.cast(value);
    }
}
