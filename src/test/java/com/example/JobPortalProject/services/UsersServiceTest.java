package com.example.JobPortalProject.services;

import com.example.JobPortalProject.entity.RecruiterProfile;
import com.example.JobPortalProject.entity.Users;
import com.example.JobPortalProject.entity.UsersType;
import com.example.JobPortalProject.repository.RecruiterProfileRepository;
import com.example.JobPortalProject.repository.UsersRepository;
import com.example.JobPortalProject.repository.UsersTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Sử dụng Mockito với JUnit 5
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RecruiterProfileRepository recruiterProfileRepository;

    @Mock
    private UsersTypeRepository usersTypeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    private Users user;
    private RecruiterProfile recruiterProfile;
    @BeforeEach
    void setUp() {
        // Tạo dữ liệu giả lập dùng chung cho các test
        UsersType recruiterType = new UsersType();
        recruiterType.setUserTypeId(1);
        recruiterType.setUserTypeName("Recruiter");

        user = new Users();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUserTypeId(recruiterType);
        user.setRegistrationDate(new Date());

        recruiterProfile= new RecruiterProfile();
        recruiterProfile.setUserId(user);

    }

    @Test
    @DisplayName("Test thêm mới User thành công")
    void testAddNewUser_Success() {
        // GIVEN: Giả lập hành vi của các dependencies

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        // WHEN: Gọi hàm cần test
        Users savedUser = usersService.addNew(user);

        // THEN: Kiểm tra kết quả
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");

        // Verify: Đảm bảo hàm save đã được gọi 1 lần
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("Test lấy User theo Email")
    void testGetUserByEmail() {
        // GIVEN
        when(usersRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // WHEN
        Optional<Users> foundUser = usersService.getUserByEmail("test@example.com");

        // THEN
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isEqualTo(1);
    }
}