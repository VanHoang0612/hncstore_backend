package com.hoang.hncstore_backend.notification.config;

import com.vonage.client.VonageClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sms.vonage")
public class VonageConfig {
    private String apiKey;
    private String apiSecret;

    @Bean
    public VonageClient vonageClient() {
        return VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }
}
