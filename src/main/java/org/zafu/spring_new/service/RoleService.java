package org.zafu.spring_new.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.zafu.spring_new.dto.request.RoleRequest;
import org.zafu.spring_new.dto.response.RoleResponse;
import org.zafu.spring_new.entity.Permission;
import org.zafu.spring_new.entity.Role;
import org.zafu.spring_new.mapper.RoleMapper;
import org.zafu.spring_new.repo.PermissionRepo;
import org.zafu.spring_new.repo.RoleRepo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepo repo;
    RoleMapper mapper;
    PermissionRepo permissionRepo;

    public RoleResponse create(RoleRequest request) {
        Role role = mapper.toRole(request);
        List<Permission> permissionList = permissionRepo.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissionList));
        return mapper.toRoleResponse(repo.save(role));
    }

    public List<RoleResponse> getAll() {
        List<Role> roles = repo.findAll();
        return roles.stream().map(mapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        repo.deleteById(role);
    }
}
