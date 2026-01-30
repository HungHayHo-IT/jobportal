package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;

    @Autowired
    public JobPostActivityController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        Object currentUserProfile  = usersService.getCurrentUserProfile(); // lấy thông tin hồ sơ của người dùng hiện tại.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//Lấy thông tin xác thực (authentication)
        // hiện tại từ SecurityContext. Trong Spring Security,đây là nơi lưu trữ thông tin về người dùng đã đăng nhập.

        if(!(authentication instanceof AnonymousAuthenticationToken)){// kiem tra xem co phai nguoi dung chua dang nhap khong
            String currentUsername = authentication.getName();
            model.addAttribute("username",currentUsername);
        }
        model.addAttribute("user",currentUserProfile);
        return "dashboard";
    }
}
