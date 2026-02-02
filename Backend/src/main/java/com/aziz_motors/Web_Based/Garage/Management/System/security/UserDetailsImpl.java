package com.aziz_motors.Web_Based.Garage.Management.System.security;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Admin;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private Admin admin;

    public UserDetailsImpl(Admin admin){
        this.admin = admin;
    }
    @Override
    public String getUsername() {
        return admin.getEmail();
    }

    @Override
    public @Nullable String getPassword() {
        return admin.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isEnabled() {
        return admin.isEmailVerified();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
}
