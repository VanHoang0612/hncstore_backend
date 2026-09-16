package com.hoang.hncstore_backend.core.config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CustomPageableResolver customPageableResolver;

    public WebConfig(CustomPageableResolver customPageableResolver) {
        this.customPageableResolver = customPageableResolver;
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableResolver);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Tự động thêm tiền tố /api/v1 cho TẤT CẢ các REST Controller trong project
        configurer.addPathPrefix("/api/v1", c -> c.isAnnotationPresent(RestController.class));
    }
}
