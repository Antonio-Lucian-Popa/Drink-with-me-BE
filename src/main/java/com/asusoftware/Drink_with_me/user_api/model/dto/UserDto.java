package com.asusoftware.Drink_with_me.user_api.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String occupation;
}
