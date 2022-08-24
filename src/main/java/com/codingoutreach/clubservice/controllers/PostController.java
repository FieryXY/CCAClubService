package com.codingoutreach.clubservice.controllers;

import com.codingoutreach.clubservice.controllers.DO.PostCreationRequest;
import com.codingoutreach.clubservice.repository.DTO.Club;
import com.codingoutreach.clubservice.repository.DTO.Post;
import com.codingoutreach.clubservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Get all posts given UUID of a club
    @GetMapping
    @RequestMapping(path = "/get/{clubId}")
    public List<Post> getPosts(@PathVariable("clubId") UUID clubId) {
        return postService.getAllPosts(clubId);
    }
    @PostMapping
    @RequestMapping(path = "/create/{clubId}")
    public void createPost(@RequestBody PostCreationRequest request, @PathVariable("clubId") UUID clubId) {
        postService.createPost(request, clubId);
    }
    //Controllers Needed: Post creation, Post editing
}
