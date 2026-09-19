package com.hoang.hncstore_backend.iam.security;

import com.hoang.hncstore_backend.core.exception.BusinessException;
import com.hoang.hncstore_backend.iam.enums.AuthCode;
import com.hoang.hncstore_backend.iam.enums.AuthProvider;
import com.hoang.hncstore_backend.iam.repository.UserCredentialRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String identifier) {
        return userCredentialRepository.findByLoginIdentifier(identifier, AuthProvider.LOCAL)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new BusinessException(AuthCode.UNAUTHORIZED, null, identifier));
    }
}
