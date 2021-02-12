package com.tutorials.oauthExercise.service;

import com.tutorials.oauthExercise.dto.PostsResponseDto;
import com.tutorials.oauthExercise.dto.PostsSaveRequestDto;
import com.tutorials.oauthExercise.dto.PostsUpdateRequestDto;
import com.tutorials.oauthExercise.entity.Posts;
import com.tutorials.oauthExercise.repository.PostsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){
        return postsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 없습니다."));
        posts.update(dto.getTitle(), dto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("게시글이 없습니다."));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

}
