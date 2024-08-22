package com.asusoftware.Drink_with_me.post_api.model.dto;

import com.asusoftware.Drink_with_me.location_api.model.dto.LocationDto;
import com.asusoftware.Drink_with_me.post_api.model.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class PostDto {

    private UUID id;
    private String description;
    //private String imageUrl;
    private LocalDateTime createdAt;
    private LocationDto location;
    private UserPostDto user;
   // private int participants;
    private long participantsCount;
    private boolean isParticipated;
    private int numberOfComments;
    private List<String> imageFilenames;

    public static PostDto fromEntityList(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .description(post.getDescription())
                //.imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .user(UserPostDto.fromEntity(post.getUser()))
               // .participants(post.getParticipants().stream().map(UserPostDto::fromEntityList).collect(Collectors.toList()))
                .location(LocationDto.toDto(post.getLocation()))
                .build();
    }

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .user(UserPostDto.fromEntity(post.getUser()))
               // .participants(post.getParticipants().stream().map(UserPostDto::fromEntity).collect(Collectors.toList()))
                .imageFilenames(post.getImageFilenames()) // Add this line
                .location(LocationDto.toDto(post.getLocation()))
                .build();
    }

    public static PostDto fromEntity(Post post, long participantsCount, boolean isParticipated) {
        return PostDto.builder()
                .id(post.getId())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .user(UserPostDto.fromEntity(post.getUser()))
                .location(LocationDto.toDto(post.getLocation()))
                .imageFilenames(post.getImageFilenames())
                .numberOfComments(post.getComments().size())
                .participantsCount(participantsCount)
                .isParticipated(isParticipated)
                .build();
    }
}