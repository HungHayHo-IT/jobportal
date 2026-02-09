package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.JobPostActivity;
import com.example.JobPortalProject.entity.JobSeekerApply;
import com.example.JobPortalProject.entity.JobSeekerProfile;
import com.example.JobPortalProject.repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository jobSeekerApplyRepository;


    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
        return jobSeekerApplyRepository.findByJob(job);
    }
}
