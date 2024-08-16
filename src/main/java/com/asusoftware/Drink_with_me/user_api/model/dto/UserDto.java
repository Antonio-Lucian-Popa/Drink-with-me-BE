package com.asusoftware.Drink_with_me.user_api.model.dto;

import com.asusoftware.Drink_with_me.user_api.model.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private String email;
    private String password;
    private String occupation;

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}