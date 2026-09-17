package com.hoang.hncstore_backend.user.mapper;

import com.hoang.hncstore_backend.core.utils.MappingUtils;
import com.hoang.hncstore_backend.user.dto.request.UserCreateRequest;
import com.hoang.hncstore_backend.user.dto.request.UserRegistrationDTO;
import com.hoang.hncstore_backend.user.dto.request.UserUpdateRequest;
import com.hoang.hncstore_backend.user.dto.response.UserDetailsResponse;
import com.hoang.hncstore_backend.user.dto.response.UserResponse;
import com.hoang.hncstore_backend.user.entity.User;
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
