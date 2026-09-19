package com.hoang.hncstore_backend.notification.utils;

public class DataNormalize {
    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "84" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }
}
