package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.services.RecruiterProfileService;
import com.example.JobPortalProject.services.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecruiterProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecruiterProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private UsersRepository usersRepository;

    @MockitoBean
    private RecruiterProfileService recruiterProfileService;

    @Test
    @WithMockUser(username = "recruiter@gmail.com", roles = "Recruiter")
    void testShowRecruiterProfilePage() throws Exception {
        // 1. Khởi tạo User và set ID (giả định ID là 1)
        Users users = new Users();
        users.setUserId(1); // Quan trọng: Controller dùng getUserId()
        users.setEmail("recruiter@gmail.com");

        // 2. Khởi tạo Profile
        RecruiterProfile recruiterProfile = new RecruiterProfile();
        recruiterProfile.setFirstName("Hung");
        recruiterProfile.setLastName("Nguyen");
        recruiterProfile.setCompany("FPT");
        recruiterProfile.setUserId(users);

        // 3. Mock đúng phương thức mà Controller gọi
        when(usersRepository.findByEmail("recruiter@gmail.com")).thenReturn(Optional.of(users));
        // Sửa findByEmail thành getOne để khớp với Controller
        when(recruiterProfileService.getOne(users.getUserId())).thenReturn(Optional.of(recruiterProfile));

        mockMvc.perform(get("/recruiter-profile/"))
                .andDo(print())
                .andExpect(status().isOk())
                // Sửa tên view cho khớp với Controller (recruiter_profile)
                .andExpect(view().name("recruiter_profile"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().attribute("profile", hasProperty("firstName", is("Hung"))))
                .andExpect(model().attribute("profile", hasProperty("company", is("FPT"))));
    }
}
