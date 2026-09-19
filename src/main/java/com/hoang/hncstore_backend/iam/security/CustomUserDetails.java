package com.hoang.hncstore_backend.iam.security;

import com.hoang.hncstore_backend.iam.entity.User;
import com.hoang.hncstore_backend.iam.entity.UserCredential;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final UserCredential userCredential;

    public CustomUserDetails(UserCredential userCredential) {
        this.userCredential = userCredential;
        this.user = userCredential.getUser();
    }

    @Override
    @NonNull
    public String getUsername() {
        if (userCredential.getUsername() != null) {
            return userCredential.getUsername();
        }
        if (userCredential.getEmail() != null) {
            return userCredential.getEmail();
        }
        return Objects.requireNonNull(userCredential.getProviderId(), "No identifier found");
    }

    @Override
    public @Nullable String getPassword() {
        return userCredential.getPassword();
    }

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });
        return authorities;
    }
}
