package com.tutorials.oauthExercise.dto;

import com.tutorials.oauthExercise.entity.Posts;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
