package org.zafu.spring_new.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.zafu.spring_new.dto.ApiResponse;
import org.zafu.spring_new.dto.request.PermissionRequest;
import org.zafu.spring_new.dto.response.PermissionResponse;
import org.zafu.spring_new.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService service;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(service.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        service.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
