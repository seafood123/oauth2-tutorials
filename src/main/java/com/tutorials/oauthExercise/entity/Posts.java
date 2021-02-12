package com.tutorials.oauthExercise.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Posts extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(length=500, nullable = false)
    private String title;

    @NonNull
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @NonNull
    private String author;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
