package com.asusoftware.Drink_with_me.user_api.model.dto;

import com.asusoftware.Drink_with_me.user_api.model.Gender;
import com.asusoftware.Drink_with_me.user_api.model.User;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UpdateUserProfileDto {

    private UUID id;
    private String profileImageUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthday;
    private Gender gender;
    private String livesIn;

    public static UpdateUserProfileDto toDto(User user) {
        UpdateUserProfileDto dto = new UpdateUserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        // dto.profileImageUrl(user.getProfileImage());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        dto.setGender(user.getGender());
        dto.setLivesIn(user.getLivesIn());
        return dto;
    }

    public static User toEntity(UserProfileDto userProfileDto) {
        User user = new User();
        user.setId(userProfileDto.getId());
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        user.setEmail(userProfileDto.getEmail());
        user.setBirthday(userProfileDto.getBirthday());
        user.setGender(userProfileDto.getGender());
        return user;
    }
}
