package com.hoang.hncstore_backend.iam.dto.user.request;

import com.hoang.hncstore_backend.core.annotation.ValueOfEnum;
import com.hoang.hncstore_backend.iam.enums.Gender;
import com.hoang.hncstore_backend.iam.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public record UserCreateRequest(
        @NotBlank(message = "validation.not_blank")
        String fullName,

        @NotBlank(message = "validation.not_blank")
        @Size(min = 10, max = 10, message = "validation.size")
        String phoneNumber,

        @Email(message = "validation.email")
        String email,

        @ValueOfEnum(enumClass = Gender.class, message = "validation.gender")
        String gender,

        MultipartFile avatarFile,

        @NotNull(message = "validation.not_null")
        @ValueOfEnum(enumClass = UserStatus.class)
        String status
) {
    public UserCreateRequest {
        fullName = StringUtils.trimToNull(fullName);
        phoneNumber = StringUtils.trimToNull(phoneNumber);
        email = StringUtils.trimToNull(email);
        gender = StringUtils.trimToNull(gender);
        status = StringUtils.trimToNull(status);
    }
}
