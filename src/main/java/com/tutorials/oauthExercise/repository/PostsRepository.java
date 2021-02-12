package com.tutorials.oauthExercise.repository;

import com.tutorials.oauthExercise.dto.PostsResponseDto;
import com.tutorials.oauthExercise.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    @Query("SELECT p From Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
