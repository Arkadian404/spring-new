package org.zafu.spring_new.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.zafu.spring_new.dto.ApiResponse;
import org.zafu.spring_new.dto.request.RoleRequest;
import org.zafu.spring_new.dto.response.RoleResponse;
import org.zafu.spring_new.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService service;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(service.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        service.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
