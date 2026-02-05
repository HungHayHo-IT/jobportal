package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.IRecruiterJobs;
import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.RecruiterJobsDto;
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

    @Test
    @DisplayName("Test lay ra danh sach job mapping tu interface sang dto")
    void testGetRecruiterJobs(){


        IRecruiterJobs mockJobs = mock(IRecruiterJobs.class);

        when(mockJobs.getJob_post_id()).thenReturn(100);
        when(mockJobs.getJob_title()).thenReturn("Back end");
        when(mockJobs.getLocationId()).thenReturn(1);
        when(mockJobs.getCity()).thenReturn("Hue");
        when(mockJobs.getState()).thenReturn("Phu Xuan");
        when(mockJobs.getCountry()).thenReturn("VietNam");
        when(mockJobs.getCompanyId()).thenReturn(1);
        when(mockJobs.getName()).thenReturn("FPT Software");
        when(mockJobs.getTotalCandidates()).thenReturn(5L);


        when(jobPostActivityRepository.getRecruiterJobs(1)).thenReturn(List.of(mockJobs));

        List<RecruiterJobsDto> recruiterJobsDtoList = jobPostActivityService.getRecruiterJobs(1);

        assertThat(recruiterJobsDtoList).isNotEmpty();
        RecruiterJobsDto dto = recruiterJobsDtoList.get(0);

        assertThat(dto.getJobPostId()).isEqualTo(100L);
        assertThat(dto.getJobTitle()).isEqualTo("Back end");
        assertThat(dto.getJobLocationId().getCity()).isEqualTo("Hue");
        assertThat(dto.getJobCompanyId().getName()).isEqualTo("FPT Software");
        assertThat(dto.getTotalCandidates()).isEqualTo(5);

    }
}