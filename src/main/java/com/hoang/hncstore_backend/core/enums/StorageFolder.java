package com.hoang.hncstore_backend.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StorageFolder {
    BRANDS("brands"),
    USERS("users"),
    ;

    private final String folderName;
}
