package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.repository.RecruiterProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecruiterProfileServiceTest {

    @Mock
    private RecruiterProfileRepository recruiterRepository;

    @InjectMocks
    private RecruiterProfileService recruiterProfileService;

    private RecruiterProfile profile;

    @BeforeEach
    void setUp() {
        profile = new RecruiterProfile();
        profile.setUserAccountId(1);
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setCity("Hanoi");
    }

    @Test
    @DisplayName("Test lấy hồ sơ Recruiter theo ID")
    void testGetRecruiterProfile() {
        // GIVEN
        when(recruiterRepository.findById(1)).thenReturn(Optional.of(profile));

        // WHEN
        Optional<RecruiterProfile> foundProfile = recruiterProfileService.getOne(1);

        // THEN
        assertThat(foundProfile).isPresent();
        assertThat(foundProfile.get().getFirstName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Test thêm/cập nhật hồ sơ Recruiter")
    void testAddNewRecruiterProfile() {
        // GIVEN
        when(recruiterRepository.save(any(RecruiterProfile.class))).thenReturn(profile);

        // WHEN
        RecruiterProfile savedProfile = recruiterProfileService.addNew(profile);

        // THEN
        assertThat(savedProfile).isNotNull();
        assertThat(savedProfile.getCity()).isEqualTo("Hanoi");
    }
}