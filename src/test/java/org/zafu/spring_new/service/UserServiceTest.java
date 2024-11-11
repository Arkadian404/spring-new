package org.zafu.spring_new.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.zafu.spring_new.dto.request.UserRequest;
import org.zafu.spring_new.dto.response.UserResponse;
import org.zafu.spring_new.entity.User;
import org.zafu.spring_new.exception.AppException;
import org.zafu.spring_new.repo.UserRepo;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepo userRepo;

    private UserRequest request;
    private User user;
    private UserResponse response;
    private LocalDate dob;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserRequest.builder()
                .username("janer")
                .firstname("doe")
                .lastname("thomas")
                .password("12345678")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id(20L)
                .username("janer")
                .firstname("doe")
                .lastname("thomas")
                .dob(dob)
                .build();

        user = User.builder()
                .id(20L)
                .username("janer")
                .firstname("doe")
                .lastname("thomas")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // given
        when(userRepo.existsByUsername(anyString())).thenReturn(false);
        when(userRepo.save(any())).thenReturn(user);
        // when
        var response = userService.createUser(request);
        // then
        Assertions.assertThat(response.getId()).isEqualTo(20L);
        Assertions.assertThat(response.getUsername()).isEqualTo("janer");
    }

    //    @Test
    //    void createUser_userExisted_failed() {
    //        // given
    //        when(userRepo.existsByUsername(anyString())).thenReturn(true);
    //        // when
    //        var exception = assertThrows(AppException.class, () -> userService.createUser(request));
    //        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(10);
    //    }

    @Test
    @WithMockUser(username = "janer")
    void getMyInfo_valid_success() {
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("janer");
        Assertions.assertThat(response.getId()).isEqualTo(20L);
    }

    @Test
    @WithMockUser(username = "janer")
    void getMyInfo_userNotFound_failed() {
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        var response = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(response.getErrorCode().getCode()).isEqualTo(15);
    }
}
