package com.hoang.hncstore_backend.iam.entity;

import com.hoang.hncstore_backend.iam.enums.CredentialStatus;
import com.hoang.hncstore_backend.iam.enums.Provider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Entity
@Table(name = "userCredentials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    String phoneNumber;

    String email;

    String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Provider provider;

    @Enumerated(EnumType.STRING)
    CredentialStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;

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
