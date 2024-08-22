package com.asusoftware.Drink_with_me.post_api.model.dto;

import com.asusoftware.Drink_with_me.user_api.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserPostDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String profileImageUrl;

    public static UserPostDto fromEntity(User user) {
        return UserPostDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImageUrl(user.getProfileImage())
                .build();
    }

    public static UserPostDto fromEntityList(User user) {
        return UserPostDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImageUrl(user.getProfileImage())
                .build();
    }
}