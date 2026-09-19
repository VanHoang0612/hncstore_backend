package com.hoang.hncstore_backend.iam.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissionEnums {
    CREATE_USER("Tao nguoi dung"),
    ;

    private final String description;

}
