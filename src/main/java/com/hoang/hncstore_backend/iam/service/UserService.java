package com.hoang.hncstore_backend.iam.service;

import com.hoang.hncstore_backend.core.enums.CommonCode;
import com.hoang.hncstore_backend.core.enums.StorageFolder;
import com.hoang.hncstore_backend.core.exception.BusinessException;
import com.hoang.hncstore_backend.core.hepler.SlugHelper;
import com.hoang.hncstore_backend.iam.dto.user.request.UserCreateRequest;
import com.hoang.hncstore_backend.iam.dto.user.request.UserUpdateAvatarRequest;
import com.hoang.hncstore_backend.iam.dto.user.request.UserUpdateRequest;
import com.hoang.hncstore_backend.iam.dto.user.response.UserDetailsResponse;
import com.hoang.hncstore_backend.iam.dto.user.response.UserResponse;
import com.hoang.hncstore_backend.iam.dto.user.response.UserUpdateAvatarResponse;
import com.hoang.hncstore_backend.iam.entity.User;
import com.hoang.hncstore_backend.iam.enums.UserLabel;
import com.hoang.hncstore_backend.iam.mapper.UserMapper;
import com.hoang.hncstore_backend.iam.repository.UserRepository;
import com.hoang.hncstore_backend.storage.StorageModuleApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final String labelKey = UserLabel.LABEL_KEY;
    private final StorageModuleApi storageModuleApi;
    private final SlugHelper slugHelper;

//    admin

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new BusinessException(CommonCode.ALREADY_EXIST, UserLabel.PHONE_NUMBER);
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(CommonCode.ALREADY_EXIST, UserLabel.EMAIL);
        }
        User user = userMapper.toUser(request);
        String slug = slugHelper.toSlug(request.phoneNumber());
        String avatarUrl = setAvatar(user, request.avatarFile(), slug);
        return userMapper.toPublicResponse(userRepository.save(user), avatarUrl);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().
                stream()
                .map(user -> {
                    String avatarUrl = storageModuleApi.getImageUrl(user.getAvatarPath(), user.getAvatarVersion());
                    return userMapper.toPublicResponse(user, avatarUrl);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDetailsResponse getUserById(UUID id) {
        User user = findById(id);
        String avatarUrl = storageModuleApi.getImageUrl(user.getAvatarPath(), user.getAvatarVersion());
        return userMapper.toDetailsResponse(user, avatarUrl);
    }

    @Transactional
    public UserDetailsResponse updateUser(UUID id, @Valid UserUpdateRequest request) {
        User user = findById(id);
        userMapper.updateUser(request, user);
        if (request.avatarFile() != null && !request.avatarFile().isEmpty()) {
            log.info("update avatar");
            String slug = slugHelper.toSlug(user.getPhoneNumber());
            String avatarUrl = setAvatar(user, request.avatarFile(), slug);
            return userMapper.toDetailsResponse(user, avatarUrl);
        }
        return userMapper.toDetailsResponse(user, storageModuleApi.getImageUrl(user.getAvatarPath(),
                user.getAvatarVersion()));
    }

    @Transactional
    public Void deleteUser(UUID id) {
        User user = findById(id);
        if (user.getAvatarPath() != null) {
            storageModuleApi.deleteImage(user.getAvatarPath());
        }
        userRepository.delete(user);
        return null;
    }


    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException(CommonCode.NOT_FOUND_WITH,
                labelKey, "id", id.toString()));
    }


//    user

    @Transactional
    public UserUpdateAvatarResponse updateAvatar(UserUpdateAvatarRequest request) {
        return null;
    }


    private String setAvatar(User user, MultipartFile avatarFile, String slug) {
        if (avatarFile != null && !avatarFile
                .isEmpty()) {
            log.info(slug);
            Map<String, Object> uploadResult = storageModuleApi.uploadImage(avatarFile,
                    StorageFolder.USERS.getFolderName(), slug);
            String publicId = (String) uploadResult.get("public_id");
            String version = uploadResult.get("version")
                    .toString();
            user.setAvatarPath(publicId);
            user.setAvatarVersion(version);

            return (String) uploadResult.get("secure_url");
        }
        if (user.getAvatarPath() != null) {
            return storageModuleApi.getImageUrl(user.getAvatarPath(), user.getAvatarVersion());
        }
        return null;
    }


}