package com.asusoftware.Drink_with_me.user_api.service;

import com.asusoftware.Drink_with_me.exception.FileStorageException;
import com.asusoftware.Drink_with_me.notification_api.model.NotificationType;
import com.asusoftware.Drink_with_me.notification_api.service.NotificationService;
import com.asusoftware.Drink_with_me.post_api.repository.PostRepository;
import com.asusoftware.Drink_with_me.user_api.exception.ImageNotFoundException;
import com.asusoftware.Drink_with_me.user_api.exception.UserNotFoundException;
import com.asusoftware.Drink_with_me.user_api.model.User;
import com.asusoftware.Drink_with_me.user_api.model.dto.UpdateUserProfileDto;
import com.asusoftware.Drink_with_me.user_api.model.dto.UserDto;
import com.asusoftware.Drink_with_me.user_api.model.dto.UserProfileDto;
import com.asusoftware.Drink_with_me.user_api.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
@Data
public class UserService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${external-link.url}")
    private String externalImagesLink;

    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserProfileDto findByIdDto(UUID profileUserId, UUID currentUserId) {
        User user = userRepository.findById(profileUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String profileImageUrl = constructImageUrlForUser(user);

        long followersCount = userRepository.countFollowersByUserId(user.getId());
        long followingCount = userRepository.countFollowingByUserId(user.getId());
        boolean isFollowing = false;
        if (currentUserId != null && !profileUserId.equals(currentUserId)) {
            isFollowing = userRepository.isUserFollowing(profileUserId, currentUserId);
        }
        UserProfileDto userProfileDto = UserProfileDto.toDto(user, followersCount, followingCount);
        userProfileDto.setFollowing(isFollowing);
        userProfileDto.setProfileImageUrl(profileImageUrl);
        return userProfileDto;
    }

    public void followUser(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + followerId));

        User following = userRepository.findById(followingId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + followingId));

        follower.getFollowing().add(following);
        following.getFollowers().add(follower);

        userRepository.save(follower);
        userRepository.save(following);

        notificationService.createNotification(follower.getId(), following.getId(), null, NotificationType.FOLLOW);

    }

    public void unfollowUser(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + followerId));

        User following = userRepository.findById(followingId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + followingId));

        follower.getFollowing().remove(following);
        following.getFollowers().remove(follower);

        userRepository.save(follower);
        userRepository.save(following);
    }

    public String uploadProfileImage(MultipartFile file, UUID userId) {
        // Find the user by ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        User user = userOptional.get();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uploadPath = uploadDir + userId;

        try {
            // Ensure directory exists. This does not create a new directory if it already exists.
            Path uploadDirPath = Paths.get(uploadPath);
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }

            // If the user already has a profile image, delete the old file
            // Check if user.getProfileImage() is not null and not empty
            if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                Path oldFilePath = uploadDirPath.resolve(user.getProfileImage());
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath); // Delete the old image
                }
            }

            // Copy the new file to the upload directory, replacing the existing file if it has the same name
            Path targetLocation = uploadDirPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Update the user's profile image with the new file name
            user.setProfileImage(fileName);
            userRepository.save(user);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + fileName, e);
        }
    }

    public Resource getProfileImage(UUID userId) {
        User user = findById(userId);
        if (user == null || user.getProfileImage() == null) {
            throw new ImageNotFoundException("Profile image not found for user with ID: " + userId);
        }

        String filename = user.getProfileImage();
        Path filePath = Paths.get(uploadDir + userId).resolve(filename);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ImageNotFoundException("Profile image not found for user with ID: " + userId);
            }
        } catch (MalformedURLException e) {
            throw new ImageNotFoundException("Profile image not found for user with ID: " + userId, e);
        }
    }

    public void deleteProfileImage(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        User user = userOptional.get();
        String fileName = user.getProfileImage();
        if (fileName == null || fileName.isEmpty()) {
            throw new FileStorageException("No profile image to delete for user: " + userId);
        }

        String uploadPath = uploadDir + userId;

        try {
            Path fileToDeletePath = Paths.get(uploadPath).resolve(fileName);
            Files.deleteIfExists(fileToDeletePath);
            user.setProfileImage(null);
            userRepository.save(user);
        } catch (IOException e) {
            throw new FileStorageException("Failed to delete file: " + fileName, e);
        }
    }
    public String updateProfileImage(MultipartFile file, UUID userId) {
        // Delete the existing image
        deleteProfileImage(userId);

        // Upload the new image
        return uploadProfileImage(file, userId);
    }


    public UpdateUserProfileDto updateUserProfile(UUID userId, UpdateUserProfileDto updatedUserDto, MultipartFile userProfileImage) {

        User findedUserRepo = userRepository.findById(userId).orElseThrow();

        findedUserRepo.setFirstName(updatedUserDto.getFirstName());
        findedUserRepo.setLastName(updatedUserDto.getLastName());
        findedUserRepo.setEmail(updatedUserDto.getEmail());
        findedUserRepo.setBirthday(updatedUserDto.getBirthday());
        findedUserRepo.setGender(updatedUserDto.getGender());
        findedUserRepo.setLivesIn(updatedUserDto.getLivesIn());

        userRepository.save(findedUserRepo);

        if (userProfileImage != null && !userProfileImage.isEmpty()) {
            uploadProfileImage(
                    userProfileImage,
                    findedUserRepo.getId()
            );
        }

        String profileImageUrl = constructImageUrlForUser(findedUserRepo);
        UpdateUserProfileDto userProfileDto = UpdateUserProfileDto.toDto(findedUserRepo);
        userProfileDto.setProfileImageUrl(profileImageUrl);
        return userProfileDto;
    }

    /**
     * Is used to load images in Front-end app from Back-end link to the folder of images
     * @param user the user which we want to see the image
     * @return return the url concatenation to view the image on the Front-end client
     */
    public String constructImageUrlForUser(User user) {
        String baseUrl = externalImagesLink;

        String imageName = user.getProfileImage();
        // Assuming the image name is based on the user's ID
        return baseUrl + user.getId() + '/' + imageName; // Adjust the file extension based on your actual image format
    }

    // TODO: find random user that is not my user. So we need to retreive users but not our user
    public List<UserDto> findRandomUsers(UUID userId) {
        return userRepository.findRandomUsers(userId).stream().map(user -> {
            String profileImageUrl = constructImageUrlForUser(user);
            UserDto userDto = UserDto.toDto(user);
            userDto.setProfileImageUrl(profileImageUrl);
            return userDto;
        }).collect(Collectors.toList());
    }

    public List<UserDto> searchUsersByName(String name) {
        return userRepository.findByUsernameStartingWith(name).stream().map(user -> {
            UserDto userDto = UserDto.toDto(user);
            String profileImageUrl = constructImageUrlForUser(user);
            userDto.setProfileImageUrl(profileImageUrl);
            return userDto;
        }).collect(Collectors.toList());
    }

    public List<UUID> checkIfFollowing(UUID followerId, List<UUID> followedIds) {
        List<User> users = userRepository.findIfFollowing(followerId, followedIds);
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    public List<UserDto> getFollowers(UUID userId) {
        List<UserDto> followers = userRepository.findFollowersByUserId(userId).stream().map(user -> {
            UserDto userDto = UserDto.toDto(user);
            String profileImageUrl = constructImageUrlForUser(user);
            userDto.setProfileImageUrl(profileImageUrl);
            return userDto;
        }).toList();
        LOGGER.info("Followers users of user " + userId + ": " + followers);
        return followers;
    }

    public List<UserDto> getFollowing(UUID userId) {
        List<UserDto> following = userRepository.findFollowingByUserId(userId).stream().map(user -> {
            UserDto userDto = UserDto.toDto(user);
            String profileImageUrl = constructImageUrlForUser(user);
            userDto.setProfileImageUrl(profileImageUrl);
            return userDto;
        }).toList();
        LOGGER.info("Following users of user " + userId + ": " + following);
        return following;
    }

    public void changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}