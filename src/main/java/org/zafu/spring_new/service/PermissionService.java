package org.zafu.spring_new.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zafu.spring_new.dto.request.PermissionRequest;
import org.zafu.spring_new.dto.response.PermissionResponse;
import org.zafu.spring_new.entity.Permission;
import org.zafu.spring_new.mapper.PermissionMapper;
import org.zafu.spring_new.repo.PermissionRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepo permissionRepo;
    PermissionMapper mapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = mapper.toPermission(request);
        return mapper.toPermissionResponse(permissionRepo.save(permission));
    }

    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepo.findAll();
        return permissions.stream().map(mapper::toPermissionResponse).toList();
    }

    public void delete(String name) {
        permissionRepo.deleteById(name);
    }
}
