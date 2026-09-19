package com.hoang.hncstore_backend.notification.service;


public interface SmsService {
    void sendSms(String phoneNumber, String message);
}
