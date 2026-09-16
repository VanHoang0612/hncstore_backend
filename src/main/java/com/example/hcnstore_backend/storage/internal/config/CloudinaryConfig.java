package com.example.hcnstore_backend.storage.internal.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "cloudinary")
@Getter
@RequiredArgsConstructor
public class CloudinaryConfig {

    private final String cloudName;
    private final String apiKey;
    private final String apiSecret;
    private final String uploadUrl;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret)
        );
    }
}
