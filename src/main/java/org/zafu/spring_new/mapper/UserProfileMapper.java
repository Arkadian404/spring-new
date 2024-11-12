package org.zafu.spring_new.mapper;

import org.mapstruct.Mapper;
import org.zafu.spring_new.dto.request.UserProfileRequest;
import org.zafu.spring_new.dto.request.UserRequest;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileRequest toUserProfileRequest(UserRequest request);
}
