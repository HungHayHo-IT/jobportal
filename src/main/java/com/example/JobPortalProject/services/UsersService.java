package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.JobSeekerProfile;
import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.JobSeekerProfileRepository;
import com.example.JobPortalProject.repository.RecruiterProfileRepository;
import com.example.JobPortalProject.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository,JobSeekerProfileRepository jobSeekerProfileRepository,RecruiterProfileRepository recruiterProfileRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if(userTypeId==1){
            recruiterProfileRepository.save(new RecruiterProfile(users));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(users));
        }
        return usersRepository.save(users);
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

}

