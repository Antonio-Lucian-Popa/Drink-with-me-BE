package com.asusoftware.Drink_with_me.post_api.model.dto;

import com.asusoftware.Drink_with_me.post_api.model.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreatePostDto {

    private String description;
    private UUID location;
    //private String imageUrl;

    public static Post toEntity(CreatePostDto createPostDto) {
        return Post.builder()
                .description(createPostDto.getDescription())
                .createdAt(LocalDateTime.now())
                //.imageUrl(imageUrl)
                .build();
    }
}
