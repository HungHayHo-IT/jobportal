package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.JobSeekerApply;
import com.example.JobPortalProject.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}

