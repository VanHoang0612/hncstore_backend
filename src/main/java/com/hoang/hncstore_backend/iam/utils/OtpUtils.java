package com.hoang.hncstore_backend.iam.utils;

import java.security.SecureRandom;

public class OtpUtils {
    public static String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(100000 + secureRandom.nextInt(900000));
    }
}
