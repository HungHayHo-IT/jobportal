package com.example.JobPortalProject.util;

import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//Lớp này triển khai UserDetails của Spring Security, cung cấp thông tin chi tiết về người dùng cho framework.
//Chuyển đổi Entity User thành đối tượng Spring Security hiểu
public class CustomUserDetails implements UserDetails {

    private final Users user;

    @Autowired
    public CustomUserDetails(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UsersType usersType = user.getUserTypeId();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usersType.getUserTypeName()));

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();// Mật khẩu đã mã hóa từ database

    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Spring Security dùng email làm username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
