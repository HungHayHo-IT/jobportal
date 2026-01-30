package com.example.JobPortalProject.config;

import com.example.JobPortalProject.services.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Đây là lớp cấu hình chính cho Spring Security.
public class WebSecurityConfig {
    //CustomerUserDetailsService: Một dịch vụ tùy chỉnh để tải thông tin người dùng từ cơ sở dữ liệu.
    private final CustomerUserDetailsService customerUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomerUserDetailsService customerUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
    //publicUrl: Một mảng các URL công khai không yêu cầu xác thực.
    private final String[] publicUrl = {"/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};



    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authenticationProvider(authenticationProvider());// Cung cấp cơ chế xác thực


        http.authorizeHttpRequests(
                auth->{
                    auth.requestMatchers(publicUrl).permitAll(); // Cho phép truy cập công khai
                    auth.anyRequest().authenticated(); // Tất cả request khác phải đăng nhập
                }
        );

        http.formLogin(form->form.loginPage("/login").permitAll()
                .successHandler(customAuthenticationSuccessHandler)
        ).logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/");
        }).cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { //Cung cấp cơ chế xác thực dựa trên Database

        //Tạo một DaoAuthenticationProvider sử dụng customerUserDetailsService để tải thông tin người dùng.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customerUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
