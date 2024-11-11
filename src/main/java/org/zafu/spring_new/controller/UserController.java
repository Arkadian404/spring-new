package org.zafu.spring_new.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.zafu.spring_new.dto.ApiResponse;
import org.zafu.spring_new.dto.request.UserRequest;
import org.zafu.spring_new.dto.request.UserUpdate;
import org.zafu.spring_new.dto.response.UserResponse;
import org.zafu.spring_new.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userDto) {
        return ApiResponse.<UserResponse>builder()
                .message("Successfully create user")
                .result(userService.createUser(userDto))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, userUpdate))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        return ApiResponse.<String>builder()
                .message("Delete user " + id + " successfully")
                .result(userService.deleteUser(id))
                .build();
    }
}
