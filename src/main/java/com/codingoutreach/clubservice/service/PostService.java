package com.codingoutreach.clubservice.service;

import com.codingoutreach.clubservice.controllers.DO.PostCreationRequest;
import com.codingoutreach.clubservice.repository.DTO.Post;
import com.codingoutreach.clubservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts(UUID clubId) {
        return postRepository.getAllPosts(clubId);
    }

    public void createPost(PostCreationRequest request, UUID clubId) {
        postRepository.insertPost(request);
    }
}
