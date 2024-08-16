package com.asusoftware.Drink_with_me.user_api.service;

import com.asusoftware.Drink_with_me.security.CustomUserDetailsService;
import com.asusoftware.Drink_with_me.security.JwtTokenUtil;
import com.asusoftware.Drink_with_me.user_api.model.User;
import com.asusoftware.Drink_with_me.user_api.model.UserRole;
import com.asusoftware.Drink_with_me.user_api.model.dto.AuthRequest;
import com.asusoftware.Drink_with_me.user_api.model.dto.UserDto;
import com.asusoftware.Drink_with_me.user_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setOccupation(userDto.getOccupation());
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));

        userRepository.save(user);

        // Generați și trimiteți token-ul de confirmare
        String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        emailService.sendConfirmationEmail(user.getEmail(), token);

        return "User registered successfully. Please check your email for confirmation.";
    }

    public String confirmUserAccount(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token); // email as username
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (jwtTokenUtil.validateToken(token, userDetailsService.loadUserByUsername(email))) {
            user.setEnabled(true);
            userRepository.save(user);
            return "Account confirmed successfully.";
        } else {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }

    public String authenticateUser(AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            User user = (User) authentication.getPrincipal();
            if (!user.getEnabled()) {
                throw new DisabledException("User account is not activated. Please check your email.");
            }

            final String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(authRequest.getEmail()));
            return token;
        } catch (DisabledException e) {
            throw new DisabledException("User account is not activated. Please check your email.", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
    }

    public String resendConfirmationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getEnabled()) {
            throw new IllegalArgumentException("User is already activated");
        }

        // Generați un nou token și trimiteți-l
        String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(email));
        emailService.sendConfirmationEmail(email, token);

        return "Confirmation email resent. Please check your email.";
    }
}
