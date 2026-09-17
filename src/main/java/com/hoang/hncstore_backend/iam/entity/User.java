package com.hoang.hncstore_backend.iam.entity;

import com.hoang.hncstore_backend.iam.enums.Gender;
import com.hoang.hncstore_backend.iam.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    @Setter(AccessLevel.NONE)
    UUID id;

    @Column(unique = true, nullable = false)
    String phoneNumber;

    @Column(unique = true)
    String email;

    String fullName;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String avatarPath;

    String avatarVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus status;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    @Column(updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    ZonedDateTime createdAt;

    @CreatedBy
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    String createdBy;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    ZonedDateTime updatedAt;

    @Setter(AccessLevel.NONE)
    @LastModifiedBy
    String updatedBy;


}
