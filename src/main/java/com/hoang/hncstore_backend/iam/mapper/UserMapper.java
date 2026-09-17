package com.hoang.hncstore_backend.iam.mapper;

import com.hoang.hncstore_backend.core.utils.MappingUtils;
import com.hoang.hncstore_backend.iam.dto.request.UserCreateRequest;
import com.hoang.hncstore_backend.iam.dto.request.UserRegistrationDTO;
import com.hoang.hncstore_backend.iam.dto.request.UserUpdateRequest;
import com.hoang.hncstore_backend.iam.dto.response.UserDetailsResponse;
import com.hoang.hncstore_backend.iam.dto.response.UserResponse;
import com.hoang.hncstore_backend.iam.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingUtils.class})
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    User toUser(UserRegistrationDTO dto);

    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

    @Mapping(target = "avatarUrl", source = "avatarUrl")
    UserResponse toPublicResponse(User user, String avatarUrl);

    @Mapping(target = "avatarUrl", source = "avatarUrl")
    UserDetailsResponse toDetailsResponse(User user, String avatarUrl);
}
