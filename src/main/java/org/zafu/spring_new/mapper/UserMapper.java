package org.zafu.spring_new.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.zafu.spring_new.dto.request.UserRequest;
import org.zafu.spring_new.dto.request.UserUpdate;
import org.zafu.spring_new.dto.response.UserResponse;
import org.zafu.spring_new.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdate userUpdate);
}
