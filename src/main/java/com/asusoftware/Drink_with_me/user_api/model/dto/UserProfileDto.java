package com.asusoftware.Drink_with_me.user_api.model.dto;

import com.asusoftware.Drink_with_me.user_api.model.Gender;
import com.asusoftware.Drink_with_me.user_api.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserProfileDto {

    private UUID id;
    private String profileImageUrl;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private Gender gender;
    private String livesIn;
    private long totalPosts;
    private List<UserDto> following;
    private List<UserDto> followers;
    private boolean userNew;


    public static UserProfileDto toDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        dto.setGender(user.getGender());
        dto.setLivesIn(user.getLivesIn());
        dto.setFollowing(user.getFollowing().stream().map(UserDto::toDto).collect(Collectors.toList()));
        dto.setFollowers(user.getFollowers().stream().map(UserDto::toDto).collect(Collectors.toList()));
        return dto;
    }
}