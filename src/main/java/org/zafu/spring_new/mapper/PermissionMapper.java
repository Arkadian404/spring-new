package org.zafu.spring_new.mapper;

import org.mapstruct.Mapper;
import org.zafu.spring_new.dto.request.PermissionRequest;
import org.zafu.spring_new.dto.response.PermissionResponse;
import org.zafu.spring_new.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
