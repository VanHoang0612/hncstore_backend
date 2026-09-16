package com.hoang.hncstore_backend;

import com.hoang.hncstore_backend.storage.internal.config.CloudinaryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableConfigurationProperties(CloudinaryConfig.class)
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class HncstoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HncstoreBackendApplication.class, args);
    }

}
