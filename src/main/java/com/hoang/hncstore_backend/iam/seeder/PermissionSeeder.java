package com.hoang.hncstore_backend.iam.seeder;

import com.hoang.hncstore_backend.iam.entity.Permission;
import com.hoang.hncstore_backend.iam.enums.PermissionEnums;
import com.hoang.hncstore_backend.iam.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Lấy tất cả các permission hiện có trong DB map theo tên
        Map<String, Permission> existingPermissions = permissionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Permission::getName, p -> p));

        for (PermissionEnums permEnum : PermissionEnums.values()) {
            String permName = permEnum.name();
            String permDesc = permEnum.getDescription();

            Permission permission = existingPermissions.get(permName);
            if (permission == null) {
                // Nếu chưa có trong DB -> Tạo mới
                permission = new Permission();
                permission.setName(permName);
                permission.setDescription(permDesc);
                permissionRepository.save(permission);
            } else {
                // Nếu đã có nhưng đổi description -> Cập nhật lại
                permission.setDescription(permDesc);
                permissionRepository.save(permission);
            }
        }
    }
}
