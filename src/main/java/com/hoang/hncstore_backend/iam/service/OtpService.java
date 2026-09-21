package com.hoang.hncstore_backend.iam.service;

import com.hoang.hncstore_backend.core.cache.RedisService;
import com.hoang.hncstore_backend.core.exception.BusinessException;
import com.hoang.hncstore_backend.iam.constants.PrefixConstants;
import com.hoang.hncstore_backend.iam.dto.auth.OtpSession;
import com.hoang.hncstore_backend.iam.dto.auth.SessionData;
import com.hoang.hncstore_backend.iam.enums.AuthCode;
import com.hoang.hncstore_backend.iam.enums.OtpPurpose;
import com.hoang.hncstore_backend.iam.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private static final Duration DEFAULT_TTL_IN_MINUTES = Duration.ofMinutes(5);
    private final ObjectMapper objectMapper;
    private final RedisService redisService;

    public OtpSession createSession(String contact, OtpPurpose purpose) {
        String sessionId = UUID.randomUUID().toString();
        String redisKey = buildOtpRedisKey(sessionId);

        SessionData sessionData = SessionData.builder()
                .contact(contact)
                .otp(OtpUtils.generateOtp())
                .purpose(purpose.name())
                .verified(false)
                .build();

        String jsonValue = objectMapper.writeValueAsString(sessionData);
        redisService.set(redisKey, jsonValue, DEFAULT_TTL_IN_MINUTES);

        return OtpSession.builder()
                .sessionId(sessionId)
                .otp(sessionData.otp())
                .ttlInMinutes(DEFAULT_TTL_IN_MINUTES.toString())
                .build();

    }

    public boolean verifyOtp(String sessionId, String otp) {
        SessionData sessionData = getRequiredSessionData(sessionId);
        if (sessionData.otp() == null) {
            log.error("OTP verification failed: otp is null");
            throw new BusinessException(AuthCode.OTP_INVALID, null);
        }
        if (sessionData.otp().equals(otp)) {
            SessionData verifiedSessionData = sessionData.withOtp(null).withVerified(true);
            redisService.set(buildOtpRedisKey(sessionId), objectMapper.writeValueAsString(verifiedSessionData),
                    DEFAULT_TTL_IN_MINUTES);
            log.info("OTP verified successfully: sessionId={} verified", sessionId);
            return true;

        } else {
            log.error("OTP verification failed: sessionId={} providedOtp={}", sessionId, otp);
            throw new BusinessException(AuthCode.OTP_INVALID, null);
        }
    }

    public boolean verifySession(String sessionId) {
        SessionData sessionData = getRequiredSessionData(sessionId);

        if (!sessionData.verified()) {
            log.error("sessionId={} not verified", sessionId);
            throw new BusinessException(AuthCode.SESSION_NOT_VERIFIED, null);
        }
        return true;
    }

    private SessionData getRequiredSessionData(String sessionId) {
        String redisKey = buildOtpRedisKey(sessionId);
        String jsonValue = redisService.get(redisKey);
        if (jsonValue == null) {
            log.error("sessionId={} not found", sessionId);
            throw new BusinessException(AuthCode.OTP_EXPIRED, null);
        }

        return objectMapper.readValue(jsonValue, SessionData.class);

    }

    private String buildOtpRedisKey(String key) {
        return PrefixConstants.OTP_PREFIX + PrefixConstants.SESSION_PREFIX + key;
    }

}
