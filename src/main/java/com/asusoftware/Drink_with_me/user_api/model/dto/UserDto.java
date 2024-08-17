package com.asusoftware.Drink_with_me.user_api.model.dto;

import com.asusoftware.Drink_with_me.user_api.model.Gender;
import com.asusoftware.Drink_with_me.user_api.model.User;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private Gender gender;
    private String livesIn;
    private Date birthday;
    private String email;
    private String password;
    private String occupation;

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setGender(user.getGender());
        dto.setLivesIn(user.getLivesIn());
        dto.setBirthday(user.getBirthday());
        dto.setOccupation(user.getOccupation());
        dto.setEmail(user.getEmail());
        return dto;
    }
}