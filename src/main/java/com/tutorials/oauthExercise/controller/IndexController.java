package com.tutorials.oauthExercise.controller;

import com.tutorials.oauthExercise.config.auth.LoginUser;
import com.tutorials.oauthExercise.config.auth.dto.SessionUser;
import com.tutorials.oauthExercise.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postService.findAllDesc());

        if(user!=null){
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
