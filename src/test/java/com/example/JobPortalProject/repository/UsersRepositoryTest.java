package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestPropertySource("/application-test.properties")
@DataJpaTest
@Transactional
public class UsersRepositoryTest {



    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @BeforeEach
    void setUp() {
        UsersType type = new UsersType();
        type.setUserTypeName("Job Seeker");
        usersTypeRepository.save(type);

        Users user = new Users();
        user.setEmail("nguyenphuocnhahungl97@gmail.com");
        user.setPassword("37xuan68");
        user.setActive(true);
        user.setUserTypeId(type);
        usersRepository.save(user);
    }



    @Test
    void testFindByEmail(){
        Optional<Users> found = usersRepository.findByEmail("nguyenphuocnhahungl97@gmail.com");

        assertTrue(found.isPresent());
        assertEquals("nguyenphuocnhahungl97@gmail.com",found.get().getEmail());

    }




}
