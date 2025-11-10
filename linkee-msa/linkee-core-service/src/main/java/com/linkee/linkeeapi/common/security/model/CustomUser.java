package com.linkee.linkeeapi.common.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {

    private final Long userId;
    private final String publicId;
    private final String email;
    private final String role;

    public CustomUser(Long userId, String publicId, String email, String role) {
        super(email, "", List.of(new SimpleGrantedAuthority(role)));
        this.userId = userId;
        this.publicId = publicId;
        this.email = email;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
