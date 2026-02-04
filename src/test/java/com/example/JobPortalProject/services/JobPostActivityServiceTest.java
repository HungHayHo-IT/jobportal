package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.JobPostActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostActivityServiceTest {

    @Mock
    private JobPostActivityRepository jobPostActivityRepository;

    @InjectMocks
    private JobPostActivityService jobPostActivityService;

    private JobPostActivity jobPost;
    private Users recruiter;

    @BeforeEach
    void setUp() {
        recruiter = new Users();
        recruiter.setUserId(1);

        jobPost = new JobPostActivity();
        jobPost.setJobPostId(100);
        jobPost.setJobTitle("Java Developer");
        jobPost.setDescriptionOfJob("Spring Boot Expert");
        jobPost.setPostedDate(new Date());
        jobPost.setPostedById(recruiter);
    }

    @Test
    @DisplayName("Test thêm bài đăng tuyển dụng mới")
    void testAddNewJobPost() {
        // GIVEN
        when(jobPostActivityRepository.save(any(JobPostActivity.class))).thenReturn(jobPost);

        // WHEN
        JobPostActivity savedJob = jobPostActivityService.addNew(jobPost);

        // THEN
        assertThat(savedJob).isNotNull();
        assertThat(savedJob.getJobTitle()).isEqualTo("Java Developer");
        verify(jobPostActivityRepository, times(1)).save(any(JobPostActivity.class));
    }


}