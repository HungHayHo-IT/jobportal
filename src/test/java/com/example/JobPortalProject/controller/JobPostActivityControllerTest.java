package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.services.JobPostActivityService;
import com.example.JobPortalProject.services.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobPostActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobPostActivityService jobPostActivityService;

    @MockitoBean
    private UsersService usersService;



    @Test
    @DisplayName("Test trang thêm việc làm (GET /dashboard/add)")
    @WithMockUser(username = "recruiter@test.com", authorities = {"Recruiter"})
    void testAddJobsPage() throws Exception {
        mockMvc.perform(get("/dashboard/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-jobs"))
                .andExpect(model().attributeExists("jobPostActivity"));
    }

    @Test
    @DisplayName("Test submit thêm việc làm mới (POST /dashboard/addNew)")
    @WithMockUser(username = "recruiter@test.com", authorities = {"Recruiter"})
    void testAddNewJob() throws Exception {
        Users mockUser = new Users();
        mockUser.setUserId(1);
        when(usersService.getCurrentUserProfile()).thenReturn(mockUser);
        when(jobPostActivityService.addNew(any(JobPostActivity.class))).thenReturn(new JobPostActivity());

        mockMvc.perform(post("/dashboard/addNew")
                        .param("jobTitle", "Senior Dev")
                        .param("descriptionOfJob", "Java/Spring expert")
                        .param("remote", "Remote-Only"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/"));
    }
}