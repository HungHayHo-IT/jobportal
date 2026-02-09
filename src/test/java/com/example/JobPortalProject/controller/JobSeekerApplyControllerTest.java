package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.services.JobPostActivityService;
import com.example.JobPortalProject.services.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
 // Import đúng package này
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Chỉ dùng cho Spring Boot 3.4+
// Nếu dùng Spring Boot cũ hơn, hãy dùng: import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

// IMPORT QUAN TRỌNG NHẤT ĐỂ SỬA LỖI CỦA BẠN
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobSeekerApplyController.class)
public class JobSeekerApplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Lưu ý: Nếu không chạy được MockitoBean thì đổi thành @MockBean
    @MockitoBean
    private JobPostActivityService jobPostActivityService;

    @MockitoBean
    private UsersService usersService;

    @Test
    @DisplayName("Test hien thi chi tiet cong viec")
    @WithMockUser(username = "hung@gmail.com" , roles = {"JOB_SEEKER"})
    public void testDisplayJobDetails() throws Exception {
        int idJob = 1;

        // Mock dữ liệu
        JobPostActivity mockJob = new JobPostActivity();
        mockJob.setJobPostId(idJob);
        mockJob.setJobTitle("Java backend");
        // Giả sử JobPostActivity có constructor hoặc setter description nếu cần thiết

        Users mockUser = new Users();
        mockUser.setUserId(100);
        mockUser.setEmail("hung@gmail.com");

        when(jobPostActivityService.getOne(idJob)).thenReturn(mockJob);
        when(usersService.getCurrentUserProfile()).thenReturn(mockUser);

        // Code chuẩn: Không có (RequestBuilder) ép kiểu
        mockMvc.perform(get("/job-details-apply/" + idJob))
                .andExpect(status().isOk())
                .andExpect(view().name("job-details"))
                .andExpect(model().attributeExists("jobDetails"))
                .andExpect(model().attributeExists("user"))
                // Lưu ý: so sánh object trong model attribute đôi khi cần equals/hashCode
                // Nếu test fail ở đây, hãy thử so sánh từng thuộc tính nhỏ
                .andExpect(model().attribute("jobDetails", mockJob))
                .andExpect(model().attribute("user", mockUser));
    }
}