package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.UserCreateDTO;
import com.ajeet.bookmyshow.dto.UserDTO;
import com.ajeet.bookmyshow.model.Role;
import com.ajeet.bookmyshow.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(dto.getRole()!=null ? com.ajeet.bookmyshow.model.Role.valueOf(dto.getRole()) : com.ajeet.bookmyshow.model.Role.USER)")
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "role", source = "role")
    UserDTO toDTO(User user);

    default String roleToString(Role role) {
        return role == null ? null : role.name();
    }

    default Role stringToRole(String s) {
        return s == null ? null : Role.valueOf(s);
    }
}
