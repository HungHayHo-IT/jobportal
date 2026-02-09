package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.JobSeekerProfile;
import com.example.JobPortalProject.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository  extends JpaRepository<JobSeekerSave,Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
