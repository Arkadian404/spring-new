package org.zafu.spring_new.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import event.dto.NotificationEvent;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zafu.spring_new.dto.request.UserProfileRequest;
import org.zafu.spring_new.dto.request.UserRequest;
import org.zafu.spring_new.dto.request.UserUpdate;
import org.zafu.spring_new.dto.response.UserResponse;
import org.zafu.spring_new.entity.Role;
import org.zafu.spring_new.entity.User;
import org.zafu.spring_new.exception.AppException;
import org.zafu.spring_new.exception.ErrorCode;
import org.zafu.spring_new.mapper.UserMapper;
import org.zafu.spring_new.mapper.UserProfileMapper;
import org.zafu.spring_new.repo.RoleRepo;
import org.zafu.spring_new.repo.UserRepo;
import org.zafu.spring_new.repo.httpclient.ProfileClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepo userRepo;
    UserMapper userMapper;
    UserProfileMapper userProfileMapper;
    ProfileClient profileClient;
    PasswordEncoder passwordEncoder;
    RoleRepo roleRepo;
    KafkaTemplate<String, Object> template;


    public UserResponse createUser(UserRequest userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepo.findById("USER").orElse(null);
        roles.add(roleUser);
        user.setRoles(roles);
        try {
            user = userRepo.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserProfileRequest userProfileRequest = userProfileMapper.toUserProfileRequest(userDto);
        userProfileRequest.setUserId(user.getId());
        var userProfileResponse = profileClient.createProfile(userProfileRequest);
        log.info(userProfileResponse.toString());

        NotificationEvent event = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(userDto.getEmail())
                .subject("Welcome to book2book")
                .body("Hello, "+userDto.getUsername())
                .build();

        template.send("notification-delivery", event);

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        log.info("Access successfully");
        return userRepo.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(Long id) {
        log.info("Still works");
        return userMapper.toUserResponse(
                userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.valueOf(context.getAuthentication().getName());
        User user = userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserUpdate userUpdate) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userMapper.updateUser(user, userUpdate);
        user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        List<Role> roles = roleRepo.findAllById(userUpdate.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepo.save(user));
    }

    public String deleteUser(Long id) {
        userRepo.deleteById(id);
        return "Successfully delete User with ID: " + id;
    }
}
