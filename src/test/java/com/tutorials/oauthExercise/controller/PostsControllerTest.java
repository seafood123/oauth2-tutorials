package com.tutorials.oauthExercise.controller;

import com.tutorials.oauthExercise.dto.PostsSaveRequestDto;
import com.tutorials.oauthExercise.dto.PostsUpdateRequestDto;
import com.tutorials.oauthExercise.entity.Posts;
import com.tutorials.oauthExercise.repository.PostsRepository;
import com.tutorials.oauthExercise.service.PostsService;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception{
        String title = "title";
        String content = "content";
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                                    .title(title)
                                    .content(content)
                                    .author("ligoke")
                                    .build();
        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        ResponseEntity<Long> entity = restTemplate.postForEntity(url, dto, Long.class);

        // then
        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0).getTitle()).isEqualTo(title);
        assertThat(list.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정() throws Exception{
        Posts savePosts = postsRepository.save(
                Posts.builder().title("title")
                            .content("content")
                            .author("author")
                            .build()
        );

        Long updateId = savePosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";
        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                                            .title(expectedTitle)
                                            .content(expectedContent)
                                            .build();
        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> entity = new HttpEntity<>(updateDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,entity,Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
}