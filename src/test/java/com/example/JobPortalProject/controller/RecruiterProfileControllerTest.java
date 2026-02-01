package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.services.RecruiterProfileService;
import com.example.JobPortalProject.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecruiterProfileController.class)
public class RecruiterProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private UsersRepository usersRepository;

    @MockitoBean
    private RecruiterProfileService recruiterProfileService;

    // RecruiterProfileRepository thường không nên gọi trực tiếp trong Controller,
    // nên dùng qua Service. Nhưng nếu controller bạn dùng thì giữ lại.
    // @MockitoBean
    // private RecruiterProfileRepository recruiterProfileRepository;

    private RecruiterProfile recruiterProfile;
    private Users users;

    @BeforeEach
    void setUp() {
        users = new Users();
        users.setUserId(1);
        users.setEmail("recruiter@gmail.com");

        recruiterProfile = new RecruiterProfile();
        recruiterProfile.setFirstName("Hung");
        recruiterProfile.setLastName("Nguyen");
        recruiterProfile.setCompany("FPT");
        recruiterProfile.setUserId(users);
        recruiterProfile.setUserAccountId(1);
    }

    @Test
    @WithMockUser(username = "recruiter@gmail.com", roles = "Recruiter")
    void testShowRecruiterProfilePage() throws Exception {
        // Mock tìm user hiện tại từ Security Context email
        when(usersRepository.findByEmail("recruiter@gmail.com"))
                .thenReturn(Optional.of(users));

        when(recruiterProfileService.getOne(users.getUserId()))
                .thenReturn(Optional.of(recruiterProfile));

        mockMvc.perform(get("/recruiter-profile/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recruiter_profile"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().attribute("profile", hasProperty("firstName", is("Hung"))));
    }

    @Test
    @WithMockUser(username = "recruiter@gmail.com", roles = "Recruiter")
    void testAddNewSuccess() throws Exception {
        when(usersRepository.findByEmail("recruiter@gmail.com"))
                .thenReturn(Optional.of(users));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                "test-image-content".getBytes()
        );

        when(recruiterProfileService.addNew(any(RecruiterProfile.class)))
                .thenReturn(recruiterProfile);

        mockMvc.perform(multipart("/recruiter-profile/addNew")
                                .file(imageFile)
                                .param("firstName", "Hung")
                                .param("lastName", "Nguyen")
                                .param("company", "FPT")
                                .with(csrf())
                        // Lưu ý: Controller nên tự lấy user từ SecurityContext thay vì nhận qua request attribute
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/")); // Khớp với UsersController

        verify(recruiterProfileService, times(1)).addNew(any(RecruiterProfile.class));
    }
}