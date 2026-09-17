package com.hoang.hncstore_backend.user.controller;

import com.hoang.hncstore_backend.core.enums.CommonCode;
import com.hoang.hncstore_backend.core.response.ApiResponse;
import com.hoang.hncstore_backend.core.response.ResponseHelper;
import com.hoang.hncstore_backend.user.dto.request.UserCreateRequest;
import com.hoang.hncstore_backend.user.dto.request.UserUpdateRequest;
import com.hoang.hncstore_backend.user.dto.response.UserDetailsResponse;
import com.hoang.hncstore_backend.user.dto.response.UserResponse;
import com.hoang.hncstore_backend.user.enums.UserLabel;
import com.hoang.hncstore_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class AdminUserController {
    private final UserService userService;
    private final ResponseHelper responseHelper;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @ModelAttribute UserCreateRequest request) {
        return responseHelper.buildSuccess(CommonCode.CREATE_SUCCESS, UserLabel.LABEL_KEY,
                userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
        return responseHelper.buildSuccess(CommonCode.GET_ALL_SUCCESS, UserLabel.LABEL_KEY, userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserById(@PathVariable UUID id) {
        return responseHelper.buildSuccess(CommonCode.GET_SUCCESS, UserLabel.LABEL_KEY,
                userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> updateUser(@PathVariable UUID id,
                                                                       @Valid @ModelAttribute UserUpdateRequest request) {
        return responseHelper.buildSuccess(CommonCode.UPDATE_SUCCESS, UserLabel.LABEL_KEY,
                userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        return responseHelper.buildSuccess(CommonCode.DELETE_SUCCESS, UserLabel.LABEL_KEY,
                userService.deleteUser(id));
    }

}
