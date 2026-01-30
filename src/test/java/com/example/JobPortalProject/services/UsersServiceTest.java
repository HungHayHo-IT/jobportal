package com.example.JobPortalProject.services;


import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import com.example.JobPortalProject.repository.JobSeekerProfileRepository;
import com.example.JobPortalProject.repository.RecruiterProfileRepository;
import com.example.JobPortalProject.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private RecruiterProfileRepository recruiterProfileRepository;

    @Mock
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @InjectMocks
    private UsersService usersService;

    private Users testUser;
    private UsersType testType;

    @BeforeEach
    void setUp(){
        testType = new UsersType();
        testType.setUserTypeId(1);
        testType.setUserTypeName("Job Seeker");

        testUser = new Users();
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("password123");
        testUser.setUserTypeId(testType);

    }

    @Test
    void testAddUserSuccess(){
        when(usersRepository.save(any(Users.class))).thenReturn(testUser);

        Users saveUser = usersService.addNew(testUser);

        assertNotNull(saveUser);
        assertEquals("test@gmail.com",saveUser.getEmail());

        verify(usersRepository,times(1)).save(testUser);
    }
}
