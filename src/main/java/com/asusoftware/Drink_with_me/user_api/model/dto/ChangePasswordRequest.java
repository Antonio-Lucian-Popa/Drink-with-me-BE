package com.asusoftware.Drink_with_me.user_api.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChangePasswordRequest {

    private UUID userId;
    private String oldPassword;
    private String newPassword;
}
