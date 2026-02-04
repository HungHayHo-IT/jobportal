package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.services.UsersService;
import com.example.JobPortalProject.services.UsersTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// QUAN TRỌNG: Các static import này giúp andExpect hoạt động
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc // Thêm dòng này để Inject MockMvc thành công
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private UsersTypeService usersTypeService;

    @Test
    @DisplayName("Test trang dang ky (Get/register)")
    void testRegisterPage() throws Exception { // Phải có throws Exception
        mockMvc.perform(get("/register")) // Sử dụng get() của MockMvcRequestBuilders
                .andExpect(status().isOk())
                // Kiểm tra HTTP 200
                .andExpect(view().name("register")
                )
                .andExpect(model().attributeExists("user"))
        ; // Kiểm tra tên file HTML trả về (nếu có)

    }

    @Test
    @DisplayName("Test dang ky thanh cong (Post/register/addNew")
    void testRegisterUser() throws Exception {


        when(usersService.getUserByEmail(any())).thenReturn(Optional.empty());
        when(usersService.addNew(any(Users.class))).thenReturn(new Users());

        mockMvc.perform(post("/register/new")
                .param("email","hung@gmail.com")
                .param("password","123")
                .param("userTypeId","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/"));


    }

    @Test
    @DisplayName("Test dang ky that bai")
    void testRegisterUserFail() throws Exception{
        String existingEmail = "hung@gmail.com";
        Users existingUser = new Users();
        existingUser.setUserId(10);
        existingUser.setEmail(existingEmail);

        when(usersService.getUserByEmail(existingEmail)).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/register/new")
                .param("email",existingEmail)
                .param("password","123456")
                .param("userTypeId","1")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("getAllTypes"))
                .andExpect(model().attributeExists("user"))

        ;
    }



    @Test
    @DisplayName("Test dawng nhap get/login")
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


}