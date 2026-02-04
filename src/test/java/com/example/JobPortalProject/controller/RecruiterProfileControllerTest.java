package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.services.RecruiterProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile; // Import này quan trọng
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart; // Dùng multipart thay vì post
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecruiterProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecruiterProfileService recruiterProfileService;

    @MockitoBean
    private UsersRepository usersRepository;

    @Test
    @DisplayName("Test hiển thị trang hồ sơ Recruiter (GET /recruiter-profile/)")
    @WithMockUser(username = "recruiter@test.com", authorities = {"Recruiter"})
    void testRecruiterProfilePage() throws Exception {
        // Giả lập dữ liệu
        Users mockUser = new Users();
        mockUser.setUserId(1);
        RecruiterProfile mockProfile = new RecruiterProfile();
        mockProfile.setFirstName("Test Name");

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(recruiterProfileService.getOne(1)).thenReturn(Optional.of(mockProfile));

        mockMvc.perform(get("/recruiter-profile/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recruiter_profile"))
                .andExpect(model().attributeExists("profile"));
    }

    @Test
    @DisplayName("Test cập nhật hồ sơ Recruiter (POST /recruiter-profile/addNew)")
    @WithMockUser(username = "recruiter@test.com", authorities = {"Recruiter"})
    void testUpdateRecruiterProfile() throws Exception {
        // GIVEN
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(new Users()));
        when(recruiterProfileService.addNew(any(RecruiterProfile.class))).thenReturn(new RecruiterProfile());

        // Tạo một file ảnh giả lập (MockMultipartFile)
        // Tham số 1 ("image"): PHẢI TRÙNG KHỚP với tên tham số trong Controller (@RequestParam("image"))
        // Tham số 2: Tên file gốc
        // Tham số 3: Loại file
        // Tham số 4: Nội dung file (byte)
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",               // <-- Tên này phải giống hệt bên Controller
                "profile.png",
                "image/png",
                "some-image-content".getBytes()
        );

        // WHEN & THEN
        // Sử dụng 'multipart' thay vì 'post'
        mockMvc.perform(multipart("/recruiter-profile/addNew")
                        .file(imageFile) // Đính kèm file vào request
                        .param("firstName", "Updated Name")
                        .param("lastName", "User")
                        .param("city", "Da Nang")
                        .param("company", "FPT Software")
                        .param("state", "Da Nang")   // Thêm các trường bắt buộc khác nếu có
                        .param("country", "Vietnam"))
                .andExpect(status().is3xxRedirection()) // Mong đợi chuyển hướng
                .andExpect(redirectedUrl("/dashboard/"));
    }
}