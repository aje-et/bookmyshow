package com.ajeet.bookmyshow.mapper;

import com.ajeet.bookmyshow.dto.UserCreateDTO;
import com.ajeet.bookmyshow.dto.UserDTO;
import com.ajeet.bookmyshow.model.Role;
import com.ajeet.bookmyshow.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toEntity_mapsRoleStringToEnum_andFields() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setEmail("alice@example.com");
        dto.setName("Alice");
        dto.setPassword("pass");
        dto.setRole("ADMIN");

        User user = mapper.toEntity(dto);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("alice@example.com");
        assertThat(user.getName()).isEqualTo("Alice");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void toDTO_convertsRoleEnumToString() {
        User user = new User();
        user.setId(5L);
        user.setEmail("bob@example.com");
        user.setName("Bob");
        user.setRole(Role.USER);

        UserDTO dto = mapper.toDTO(user);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getRole()).isEqualTo("USER");
    }
}
