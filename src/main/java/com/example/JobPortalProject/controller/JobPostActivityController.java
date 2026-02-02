package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.services.JobPostActivityService;
import com.example.JobPortalProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
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

    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity",new JobPostActivity());
        model.addAttribute("user",usersService.getCurrentUserProfile());
        return "add-jobs";

    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity , Model model){

        Users users = usersService.getCurrentUser(); // lay quyen user hien tai
        if(users !=null){
            jobPostActivity.setPostedById(users);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity",jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }
}
