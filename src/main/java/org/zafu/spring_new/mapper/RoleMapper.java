package org.zafu.spring_new.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zafu.spring_new.dto.request.RoleRequest;
import org.zafu.spring_new.dto.response.RoleResponse;
import org.zafu.spring_new.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
