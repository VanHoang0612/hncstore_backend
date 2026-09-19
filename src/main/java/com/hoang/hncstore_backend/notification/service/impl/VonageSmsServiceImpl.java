package com.hoang.hncstore_backend.notification.service.impl;

import com.hoang.hncstore_backend.core.enums.CommonCode;
import com.hoang.hncstore_backend.core.exception.BusinessException;
import com.hoang.hncstore_backend.notification.service.SmsService;
import com.hoang.hncstore_backend.notification.utils.DataNormalize;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("vonage")
@RequiredArgsConstructor
@Slf4j
public class VonageSmsServiceImpl implements SmsService {
    private final VonageClient vonageClient;


    @Override
    public void sendSms(String phoneNumber, String message) {
        String normalizedPhoneNumber = DataNormalize.normalizePhoneNumber(phoneNumber);
        TextMessage textMessage = new TextMessage("HNC Store", normalizedPhoneNumber, message);
        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(textMessage);

        if (response.getMessages().getFirst().getStatus() == MessageStatus.OK) {
            log.info("Send sms to {} successfully", phoneNumber);

        } else {
            String errorMessage = response.getMessages().getFirst().getErrorText();
            log.error("Failed to send sms to {}: {}", phoneNumber, errorMessage);
            throw new BusinessException(CommonCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
