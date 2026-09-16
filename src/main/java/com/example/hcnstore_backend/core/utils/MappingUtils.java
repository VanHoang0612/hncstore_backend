package com.hoang.hncstore_backend.core.utils;

import org.mapstruct.Condition;

public class MappingUtils {
    @Condition
    public static String trimToNull(String str) {
        if (str != null && str.trim().isEmpty()) {
            return null;
        }
        return str;
    }
}
