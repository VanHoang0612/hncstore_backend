package com.hoang.hncstore_backend.iam.repository;

import com.hoang.hncstore_backend.iam.entity.UserCredential;
import com.hoang.hncstore_backend.iam.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

    @Query("SELECT uc FROM UserCredential uc JOIN FETCH uc.user u JOIN FETCH u.roles " +
            "WHERE (uc.username = :loginIdentifier OR uc.phoneNumber=:loginIdentifier) " +
            "AND uc.provider = :authProvider")
    Optional
            <UserCredential> findByLoginIdentifier(@Param("loginIdentifier") String loginIdentifier,
                                                   @Param("authProvider") AuthProvider authProvider);
}
