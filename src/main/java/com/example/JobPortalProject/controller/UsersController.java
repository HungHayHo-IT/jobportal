package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import com.example.JobPortalProject.services.UsersService;
import com.example.JobPortalProject.services.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());

        // Kiểm tra nếu email ĐÃ tồn tại
        if (optionalUsers.isPresent()) {
            model.addAttribute("error", "Email already registered, try to login or register with other email.");
            List<UsersType> usersTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersTypes);

            // SỬA LỖI Ở ĐÂY: Đổi "users" thành "user" (số ít)
            // Và nên trả lại đối tượng 'users' cũ để giữ lại dữ liệu form
            model.addAttribute("user", users);

            return "register";
        }

        // Lưu người dùng mới
        usersService.addNew(users);
        return "redirect:/dashboard/";
    }

    // THÊM ĐOẠN NÀY ĐỂ SỬA LỖI 404 DASHBOARD
    @GetMapping("/dashboard/")
    public String dashboard() {
        return "dashboard"; // Trả về file dashboard.html
    }
}