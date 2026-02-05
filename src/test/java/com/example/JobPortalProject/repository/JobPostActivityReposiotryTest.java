package com.example.JobPortalProject.repository;

import com.example.JobPortalProject.entity.IRecruiterJobs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("/application-test.properties")
public class JobPostActivityReposiotryTest {

    @Autowired
    private JobPostActivityRepository repository;

    @Test
    @Sql(scripts = "/test-data.sql")
    void testGetRecruiterJobs() {

        List<IRecruiterJobs> list = repository.getRecruiterJobs(1);

        assertThat(list).hasSize(2); // Vì trong SQL ta insert 2 job cho User 1
        assertThat(list.get(0).getJob_title()).isEqualTo("Senior Java Developer");
    }
}
