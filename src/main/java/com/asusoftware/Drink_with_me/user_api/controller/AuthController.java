package com.asusoftware.Drink_with_me.user_api.controller;

import com.asusoftware.Drink_with_me.user_api.model.dto.AuthRequest;
import com.asusoftware.Drink_with_me.user_api.model.dto.UserDto;
import com.asusoftware.Drink_with_me.user_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart("userDto") UserDto userDto, @RequestPart(value = "file", required = false) MultipartFile file) {
        String response = authService.registerUser(userDto, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String token) {
        String response = authService.confirmUserAccount(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        String token = authService.authenticateUser(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<?> resendConfirmationEmail(@RequestParam("email") String email) {
        String response = authService.resendConfirmationEmail(email);
        return ResponseEntity.ok(response);
    }

    static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
