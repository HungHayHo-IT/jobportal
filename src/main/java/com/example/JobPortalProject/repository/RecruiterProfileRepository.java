package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {
}
