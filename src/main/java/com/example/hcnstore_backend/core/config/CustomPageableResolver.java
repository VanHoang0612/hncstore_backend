package com.example.hcnstore_backend.core.config;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomPageableResolver extends PageableHandlerMethodArgumentResolver {
    @NonNull
    @Override
    public Pageable resolveArgument(@NonNull MethodParameter methodParameter,
                                    @Nullable ModelAndViewContainer mavContainer,
                                    @NonNull NativeWebRequest webRequest,
                                    @Nullable WebDataBinderFactory binderFactory) {
        Pageable pageable = (Pageable) super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        String pageStr = webRequest.getParameter("page");
        int adjustedPage;
        if (pageStr != null && !pageStr.isEmpty()) {
            // Nếu có truyền (VD: page=1, page=2...), ta coi đây là chuẩn 1-based của UI và trừ đi 1
            int uiPage = Integer.parseInt(pageStr);
            adjustedPage = (uiPage > 1) ? uiPage - 1 : 0;
        } else {
            // Nếu KHÔNG truyền page, ta kiểm tra xem @PageableDefault đang đặt mặc định là mấy
            // pageable.getPageNumber() lúc này chính là giá trị mặc định do Spring/PageableDefault quy định
            adjustedPage = pageable.getPageNumber();
        }

        // 3. Trả về Pageable hoàn chỉnh cho Database
        return PageRequest.of(adjustedPage, pageable.getPageSize(), pageable.getSort());
    }
}
