package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
@TestPropertySource("/application-test.properties")
public class RecruiterProfileTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Test
    void testSaveAndFindRecruiterProfile(){
        UsersType usersType = new UsersType();
        usersType.setUserTypeName("Recruiter");
        usersTypeRepository.save(usersType);

        Users users = new Users();
        users.setEmail("recruiter@gmail.com");
        users.setPassword("123");
        users.setUserTypeId(usersType);
        usersRepository.save(users);

        RecruiterProfile recruiterProfile = new RecruiterProfile();
        recruiterProfile.setFirstName("Hung");
        recruiterProfile.setLastName("Nguyen");
        recruiterProfile.setCompany("Fpt");
        recruiterProfile.setCity("Hue");
        recruiterProfile.setUserId(users);
        recruiterProfileRepository.save(recruiterProfile);

        Optional<RecruiterProfile> foundProfile = recruiterProfileRepository.findById(users.getUserId());
        assertTrue(foundProfile.isPresent());
        assertEquals("Fpt", foundProfile.get().getCompany());

    }
}
