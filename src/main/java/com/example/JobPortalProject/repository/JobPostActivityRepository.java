package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostActivityRepository extends JpaRepository<JobPostActivity,Integer> {
}
