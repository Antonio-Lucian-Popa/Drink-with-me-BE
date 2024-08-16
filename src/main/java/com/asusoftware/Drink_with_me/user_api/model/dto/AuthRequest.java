package com.asusoftware.Drink_with_me.user_api.model.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
