package net.vtstar.codegenerator.base.utils;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

public final class ParamAssert extends Assert {
    public static void notEmpty(String string, String msg) {
        if (StringUtils.isEmpty(string)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Collection<?> collection, String message, Object... object) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    public static void notNull(Object object, String message, Object... param) {
        if (object == null) {
            throw new IllegalArgumentException(String.format(message, param));
        }
    }

    public static void isNull(Object object, String message, Object... param) {
        if (object != null) {
            throw new IllegalArgumentException(String.format(message, param));
        }
    }

    public static void isTrue(boolean expression, String message, Object... object) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, object));
        }
    }

    private ParamAssert() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
