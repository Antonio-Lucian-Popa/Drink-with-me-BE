package com.asusoftware.Drink_with_me.post_api.service;

import com.asusoftware.Drink_with_me.location_api.model.County;
import com.asusoftware.Drink_with_me.location_api.repository.CountyRepository;
import com.asusoftware.Drink_with_me.notification_api.model.NotificationType;
import com.asusoftware.Drink_with_me.notification_api.service.NotificationService;
import com.asusoftware.Drink_with_me.post_api.exception.PostNotFoundException;
import com.asusoftware.Drink_with_me.post_api.exception.StorageException;
import com.asusoftware.Drink_with_me.location_api.model.Location;
import com.asusoftware.Drink_with_me.post_api.model.Post;
import com.asusoftware.Drink_with_me.post_api.model.dto.CreatePostDto;
import com.asusoftware.Drink_with_me.post_api.model.dto.PostDto;
import com.asusoftware.Drink_with_me.post_api.model.dto.PostImageDto;
import com.asusoftware.Drink_with_me.post_api.model.dto.UserPostDto;
import com.asusoftware.Drink_with_me.location_api.repository.LocationRepository;
import com.asusoftware.Drink_with_me.post_api.repository.PostRepository;
import com.asusoftware.Drink_with_me.user_api.model.User;
import com.asusoftware.Drink_with_me.user_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
public class PostService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${external-link.url}")
    private String externalImagesLink;

    private final PostRepository postRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final LocationRepository locationRepository;
    private final CountyRepository countyRepository;

    @Transactional
    public PostDto createPostWithImages(UUID userId, CreatePostDto createPostDto, List<MultipartFile> files) {
        User user = userService.findById(userId);
        Post post = CreatePostDto.toEntity(createPostDto);
        Location location = locationRepository.findById(createPostDto.getLocation()).orElseThrow(EntityNotFoundException::new);
        post.setLocation(location);
        post.setUser(user);
        Post postSaved = postRepository.save(post); // This saves the post and gives it an ID

        if(files != null) {
            // Then, save the images and bind them to the post
            List<String> filenames = files.stream()
                    .map(file -> saveImage(file, postSaved.getId()))
                    .collect(Collectors.toList());

            // Update the Post entity with the image references
            postSaved.setImageFilenames(filenames);
        }
        postRepository.save(postSaved); // Update the post record with image references
        postSaved.setParticipants(new HashSet<>());
        // Convert the updated Post entity to a DTO to return
        PostDto postDto = PostDto.fromEntity(postSaved);
        List<String> imageUrls = constructImageUrlsForPost(postSaved.getId());
        postDto.setImageFilenames(imageUrls); // Set image URLs for the post
        return postDto;
    }

    @Transactional
    public PostDto updateExistingPost(UUID postId, UUID userId, CreatePostDto updatePostDto, List<MultipartFile> newImages) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User does not have permission to update this post");
        }

        // Update post details here...
        post.setDescription(updatePostDto.getDescription());
        Location location = locationRepository.findById(updatePostDto.getLocation()).orElseThrow(EntityNotFoundException::new);
        post.setLocation(location);

        // Handle new images
        if(newImages != null && !newImages.isEmpty()) {
            List<String> addedImageFilenames = newImages.stream()
                    .map(file -> saveImage(file, post.getId())).toList();

            // Finalize the list of image filenames for the post
            List<String> finalImageFilenames = new ArrayList<>(post.getImageFilenames());
            finalImageFilenames.addAll(addedImageFilenames);
            post.setImageFilenames(finalImageFilenames);
        }

        postRepository.save(post);

        // Construct PostDto...
        PostDto postDto = PostDto.fromEntity(post);
        postDto.getUser().setProfileImageUrl(constructImageUrlForUser(post.getUser().getId()));
        // Assuming constructImageUrlsForPost is adapted to handle a list of filenames and construct URLs
        List<String> imageUrls = constructImageUrlsForPost(post.getId());
        postDto.setImageFilenames(imageUrls);

        return postDto;
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPostsByLocation(UUID countyId, UUID cityId, Pageable pageable) {
        // Fetch the county by ID, throw exception if not found
        County county = countyRepository.findById(countyId)
                .orElseThrow(() -> new RuntimeException("County not found"));

        // Fetch the location by city ID and county, throw exception if not found
        Location location = locationRepository.findByIdAndCounty(cityId, county)
                .orElseThrow(() -> new RuntimeException("Location not found in the specified county"));

        // Find posts by location and return a paginated list of PostDto
        return postRepository.findPostsWithParticipantsCountByLocation(location.getId(), pageable)
                .map(PostDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(UUID userId, Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> {
                    String userProfileImageUrl = constructImageUrlForUser(post.getUser().getId());
                    post.getUser().setProfileImage(userProfileImageUrl);

                    // Verificăm dacă utilizatorul conectat a participat la postare
                    boolean isParticipated = post.getParticipants().stream()
                            .anyMatch(participant -> participant.getId().equals(userId));

                    return PostDto.fromEntity(post, post.getParticipants().size(), isParticipated);
                });
    }

    private String saveImage(MultipartFile file, UUID postId) {
        if (file.isEmpty()) {
            throw new StorageException("Cannot store empty file.");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            // Ensure the directory structure /posts/{postId}/ exists
            Path directory = Paths.get(uploadDir, "posts", postId.toString()).toAbsolutePath().normalize();
            Files.createDirectories(directory); // This will create all non-existent parent directories

            // Construct the final file path
            Path destinationFile = directory.resolve(originalFilename).normalize();

            // Security check to ensure we're not saving the file outside of the intended directory
            if (!destinationFile.getParent().equals(directory)) {
                throw new StorageException("Cannot store file outside of the specified directory.");
            }

            // Save the file
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            // Return the relative path to the saved file
            return Paths.get("posts", postId.toString(), originalFilename).toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public long countPostsByUserId(UUID userId) {
        return postRepository.countPostsByUserId(userId);
    }


    public Page<PostDto> findAllFollowingUsersPosts(UUID userId, Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findFollowingAndUsersPosts(userId, pageableWithSorting);

        return postPage.map(post -> {
            UserPostDto userPostDto = UserPostDto.fromEntity(post.getUser());

            String userProfileImageUrl = constructImageUrlForUser(post.getUser().getId());
            userPostDto.setProfileImageUrl(userProfileImageUrl); // Set user's profile image URL

            PostDto postDto = PostDto.fromEntity(post);
            postDto.setUser(userPostDto);
            postDto.setNumberOfComments(post.getComments().size());
            postDto.setCreatedAt(post.getCreatedAt());

            // Verificăm dacă utilizatorul conectat a participat la postare
            boolean isParticipated = post.getParticipants().stream()
                    .anyMatch(participant -> participant.getId().equals(userId));
            postDto.setParticipated(isParticipated);
            postDto.setParticipantsCount(post.getParticipants().size());

         /*   List<UserPostDto> userLikesPostDtoList = postDto.getParticipants().stream().peek(user -> {
                String userLikeProfileImageUrl = constructImageUrlForUser(user.getId());
                user.setProfileImageUrl(userLikeProfileImageUrl);
            }).toList();
            postDto.setParticipants(userLikesPostDtoList); */

            // Assuming each post can have multiple images, construct URLs for them
            List<String> imageUrls = constructImageUrlsForPost(post.getId());
            postDto.setImageFilenames(imageUrls); // Set image URLs for the post

            return postDto;
        });
    }

    private List<String> constructImageUrlsForPost(UUID postId) {
        String baseUrl = externalImagesLink + "posts/";
        Path postImagesDir = Paths.get(uploadDir, "posts", postId.toString()).toAbsolutePath().normalize();

        List<String> imageUrls = new ArrayList<>();
        try {
            if (Files.exists(postImagesDir)) {
                Files.list(postImagesDir).forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        // Construct a publicly accessible URL for each image
                        String imageUrl = baseUrl + postId + '/' + filePath.getFileName().toString();
                        imageUrls.add(imageUrl);
                    }
                });
            }
        } catch (IOException e) {
            // Log error or handle it according to your application's policies
            System.err.println("Error listing images for post: " + e.getMessage());
        }

        return imageUrls;
    }

    /**
     * Is used to load images in Front-end app from Back-end link to the folder of images
     * @param userId the user id which we want to see the image
     * @return return the url concatenation to view the image on the Front-end client
     */
    public String constructImageUrlForUser(UUID userId) {
        String baseUrl = externalImagesLink;

        User user = userService.findById(userId);
        String imageName = user.getProfileImage();
        // Assuming the image name is based on the user's ID
        return baseUrl + userId + '/' + imageName; // Adjust the file extension based on your actual image format
    }


    public Page<PostDto> findPostsByUserId(UUID userId, Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findByUserId(userId, pageableWithSorting);

        return postPage.map(post -> {
            UserPostDto userPostDto = UserPostDto.fromEntity(post.getUser());

            // Construct the URL for the user's profile image
            String userProfileImageUrl = constructImageUrlForUser(post.getUser().getId());
            userPostDto.setProfileImageUrl(userProfileImageUrl);

            PostDto postDto = PostDto.fromEntity(post);
            postDto.setUser(userPostDto);
            postDto.setNumberOfComments(post.getComments().size());
            postDto.setCreatedAt(post.getCreatedAt());
            // Verificăm dacă utilizatorul conectat a participat la postare
            boolean isParticipated = post.getParticipants().stream()
                    .anyMatch(participant -> participant.getId().equals(userId));
            postDto.setParticipated(isParticipated);
            postDto.setParticipantsCount(post.getParticipants().size());
/*
            List<UserPostDto> userLikesPostDtoList = postDto.getParticipants().stream().peek(user -> {
                String userLikeProfileImageUrl = constructImageUrlForUser(user.getId());
                user.setProfileImageUrl(userLikeProfileImageUrl);
            }).toList();
            postDto.setParticipants(userLikesPostDtoList); */

            // Assuming each post can have multiple images, construct URLs for them
            List<String> imageUrls = constructImageUrlsForPost(post.getId());
            postDto.setImageFilenames(imageUrls); // Set image URLs for the post

            return postDto;
        });
    }


    public Post findById(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("Post not found with id: " + id));
    }

    public void deletePostById(UUID id, UUID userId) {
        Post post = postRepository.findByIdAndUserId(id, userId);
        postRepository.delete(post);
    }

    @Transactional
    public PostDto participantPost(UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post not found with id: " + postId));

        User participantUser = userService.findById(userId);

        Set<User> participants = post.getParticipants();
        if (!participants.contains(participantUser)) {
            participants.add(participantUser);
            post.setParticipants(participants);
            postRepository.save(post);

            // if the current user likes his posts the notification doesn't need to trigger
            if(participantUser.getId() != post.getUser().getId()) {
                // Create and save the notification for the post owner
                notificationService.createNotification(participantUser.getId(), post.getUser().getId(), postId, NotificationType.PARTICIPANT);
            }
        }
        long participantsCount = post.getParticipants().size();
        // Verificăm dacă utilizatorul conectat a participat la postare
        boolean isParticipated = post.getParticipants().stream()
                .anyMatch(participant -> participant.getId().equals(userId));
        return PostDto.fromEntity(post, participantsCount, isParticipated);
    }

    public PostDto removeParticipantPost(UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException("Post not found with id: " + postId));

        User participantToRemove = userService.findById(userId);

        Set<User> participants = post.getParticipants();
        participants.remove(participantToRemove);

        post.setParticipants(participants);
        postRepository.save(post);
        long participantsCount = post.getParticipants().size();
        // Verificăm dacă utilizatorul conectat a participat la postare
        boolean isParticipated = post.getParticipants().stream()
                .anyMatch(participant -> participant.getId().equals(userId));
        return PostDto.fromEntity(post, participantsCount, isParticipated);
    }

    @Transactional
    public void removeImagesByUrls(UUID postId, List<String> imageUrls) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        imageUrls.forEach(url -> {
            String filename = extractFilenameFromUrl(url);
            Path fileToDeletePath = Paths.get(uploadDir, "posts", postId.toString(), filename);

            try {
                Files.deleteIfExists(fileToDeletePath);
                // Remove the filename from the post's imageFilenames
                post.getImageFilenames().remove(filename);
                // Log success or handle accordingly
            } catch (IOException e) {
                // Log failure to delete or handle accordingly
                e.printStackTrace();
            }
        });

        // Save the post entity to persist the removal of image filenames
        postRepository.save(post);
    }

    public Page<PostImageDto> getFirstImageFromEachPost(Pageable pageable) {
        // Fetch the page of posts that supposedly have images
        Page<Post> page = postRepository.findPostsWithImages(pageable);

        // Filter and map to DTOs, only including posts with valid, non-empty image URLs
        List<PostImageDto> filteredPosts = page.getContent().stream()
                .filter(post -> !post.getImageFilenames().isEmpty()) // Ensure there are image filenames
                .map(post -> {
                    List<String> imageUrls = constructImageUrlsForPost(post.getId());
                    if (!imageUrls.isEmpty()) { // Check if image URLs are indeed non-empty
                        PostImageDto postImageDto = PostImageDto.toDto(post);
                        postImageDto.setImageUrl(imageUrls.get(0)); // Set the first available image URL
                        return postImageDto;
                    }
                    return null; // Return null if no valid image URLs are found
                })
                .filter(Objects::nonNull) // Remove any nulls from the list of DTOs
                .collect(Collectors.toList());

        // Return as a PageImpl to preserve Page properties but with filtered posts only
        return new PageImpl<>(filteredPosts, pageable, filteredPosts.size());
    }

    public PostDto findPostById(UUID id) {
        Post post = postRepository.findById(id) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id));
        PostDto postDto = PostDto.fromEntity(post);
        UserPostDto userDto = postDto.getUser();
        userDto.setProfileImageUrl(constructImageUrlForUser(userDto.getId()));
        postDto.setUser(userDto);
        List<String> images = constructImageUrlsForPost(id);
        postDto.setImageFilenames(images);
        postDto.setNumberOfComments(post.getComments().size());
        return postDto;
    }


    public List<PostImageDto> getImageUrlsByUser(UUID userId) {
        return postRepository.findPostsByUserId(userId)
                .stream()
                .flatMap(post -> constructImageUrlsForPost(post).stream())
                .sorted(Comparator.comparing(PostImageDto::getImageUrl).reversed()) // Sort by image URL (or another criteria)
                .limit(4) // Limit to the last 4 images
                .collect(Collectors.toList());
    }

    private List<PostImageDto> constructImageUrlsForPost(Post post) {
        String baseUrl = externalImagesLink + "posts/";
        UUID postId = post.getId();
        Path postImagesDir = Paths.get(uploadDir, "posts", postId.toString()).toAbsolutePath().normalize();

        List<PostImageDto> imageUrls = new ArrayList<>();
        try {
            if (Files.exists(postImagesDir)) {
                Files.list(postImagesDir).forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        // Construct a publicly accessible URL for each image
                        String imageUrl = baseUrl + postId + '/' + filePath.getFileName().toString();
                        imageUrls.add(PostImageDto.toDto(post, imageUrl));
                    }
                });
            }
        } catch (IOException e) {
            // Log error or handle it according to your application's policies
            System.err.println("Error listing images for post: " + e.getMessage());
        }

        return imageUrls;
    }
    private String extractFilenameFromUrl(String url) {
        // This implementation needs to be adjusted based on how your URLs are structured.
        // Assuming the URL ends with "/posts/{postId}/{filename}", here's a simple extraction:
        return url.substring(url.lastIndexOf('/') + 1);
    }
}