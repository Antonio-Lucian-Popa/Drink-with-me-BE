package com.asusoftware.Drink_with_me.user_api.service;

import com.asusoftware.Drink_with_me.exception.FileStorageException;
import com.asusoftware.Drink_with_me.security.CustomUserDetailsService;
import com.asusoftware.Drink_with_me.security.JwtTokenUtil;
import com.asusoftware.Drink_with_me.user_api.exception.UserNotFoundException;
import com.asusoftware.Drink_with_me.user_api.model.User;
import com.asusoftware.Drink_with_me.user_api.model.UserRole;
import com.asusoftware.Drink_with_me.user_api.model.dto.AuthRequest;
import com.asusoftware.Drink_with_me.user_api.model.dto.UserDto;
import com.asusoftware.Drink_with_me.user_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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

    @Value("${upload.dir}")
    private String uploadDir;

    @Transactional
    public String registerUser(UserDto userDto, MultipartFile file) {
        try {
            if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email is already taken!");
            }

            // Create a new user entity
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setOccupation(userDto.getOccupation());
            user.setRole(UserRole.ROLE_USER);
            user.setGender(userDto.getGender());
            user.setBirthday(userDto.getBirthday());
            user.setEnabled(false);

            // Save the user to the database
            User savedUser = userRepository.save(user);

            // Handle profile image upload
            if (file != null && !file.isEmpty()) {
                userService.uploadProfileImage(file, savedUser.getId());
            } else {
                User userWithProfileImage = setDefaultProfileImage(savedUser.getId());
                userRepository.save(userWithProfileImage);
            }

            // Generate the confirmation token
            String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(savedUser.getEmail()));

            // Send the confirmation email
            emailService.sendConfirmationEmail(user.getEmail(), token);

            return "User registered successfully. Please check your email for confirmation.";

        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();

            // Rollback the transaction in case of any failure
            throw new RuntimeException("User registration failed. Transaction rolled back.", e);
        }
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

    public User setDefaultProfileImage(UUID userId) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        String uploadPath = uploadDir + "/" + userId;
        Path uploadDirPath = Paths.get(uploadPath);

        try {
            // Ensure the user-specific directory exists
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            // Define the default profile image path and the filename for the user-specific copy
            Path defaultImagePath = Paths.get(uploadDir, "newUserDefaultProfileImage", "profile-image.png");
            String fileName = "default-profile.png"; // Or generate a unique name if preferred

            // Copy the default image to the user's directory
            Path targetLocation = uploadDirPath.resolve(fileName);
            Files.copy(defaultImagePath, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // If the user already has a profile image different from the default, consider deleting the old file
            // This part is optional and depends on your application's requirements
            if (user.getProfileImage() != null && !user.getProfileImage().equals(uploadPath + "/" + fileName)) {
                Path oldFilePath = uploadDirPath.resolve(user.getProfileImage());
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath); // Delete the old image
                }
            }

            // Update the user's profile image path with the default image's path or filename
            user.setProfileImage(fileName); // Adjust according to how you handle image paths
            return user;

        } catch (IOException e) {
            throw new FileStorageException("Could not set default profile image for user with ID: " + userId, e);
        }
    }

}
