package com.hoang.hncstore_backend.user.dto.request;

import com.hoang.hncstore_backend.core.annotation.ValueOfEnum;
import com.hoang.hncstore_backend.user.enums.Gender;
import com.hoang.hncstore_backend.user.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdateRequest(

        String fullName,

        @Size(min = 10, max = 10, message = "validation.size")
        String phoneNumber,

        @Email(message = "validation.email")
        String email,

        @ValueOfEnum(enumClass = Gender.class, message = "validation.gender")
        String gender,

        MultipartFile avatarFile,

        @ValueOfEnum(enumClass = UserStatus.class)
        String status
) {
    public UserUpdateRequest {
        fullName = StringUtils.trimToNull(fullName);
        phoneNumber = StringUtils.trimToNull(phoneNumber);
        email = StringUtils.trimToNull(email);
        gender = StringUtils.trimToNull(gender);
        status = StringUtils.trimToNull(status);
    }
}
