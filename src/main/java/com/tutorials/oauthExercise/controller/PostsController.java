package com.tutorials.oauthExercise.controller;

import com.tutorials.oauthExercise.dto.PostsResponseDto;
import com.tutorials.oauthExercise.dto.PostsSaveRequestDto;
import com.tutorials.oauthExercise.dto.PostsUpdateRequestDto;
import com.tutorials.oauthExercise.entity.Posts;
import com.tutorials.oauthExercise.repository.PostsRepository;
import com.tutorials.oauthExercise.service.PostsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PostsController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto dto){
        return postsService.update(id, dto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

}
