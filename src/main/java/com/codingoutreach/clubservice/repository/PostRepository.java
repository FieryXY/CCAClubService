package com.codingoutreach.clubservice.repository;

import com.codingoutreach.clubservice.repository.DTO.Post;
import com.codingoutreach.clubservice.repository.DTO.PostTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PostRepository {
    // SQL Queries
    private final String GET_ALL_POSTS = "SELECT * FROM post WHERE sender=?";

    private final String GET_ALL_POST_TABS = "SELECT * FROM post_tab WHERE post_id=?";



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> getAllPosts(UUID clubId) {
        List<Post> temp = jdbcTemplate.query(GET_ALL_POSTS, new Object[] {clubId}, mapPost());
        for (Post i : temp) {
            UUID postId = i.getPost_id();
            List<PostTab> postTabs = jdbcTemplate.query(GET_ALL_POST_TABS, new Object[] {postId}, mapPostTabs());
            i.setPostTab(postTabs);
        }
        return temp;
    }

    public RowMapper<Post> mapPost() {
        return ((resultSet, i) -> {
            UUID post_id = UUID.fromString(resultSet.getString("post_id"));
            UUID sender = UUID.fromString(resultSet.getString("sender"));
            String title = resultSet.getString("title");
            return new Post(post_id, sender, title, null);
        });
    }
    public RowMapper<PostTab> mapPostTabs() {
        return ((resultSet, i) -> {
            UUID tabId = UUID.fromString(resultSet.getString("tab_id"));
            UUID postId = UUID.fromString(resultSet.getString("post_id"));
            String header = resultSet.getString("header");
            String textContent = resultSet.getString("text_content");
            String mediaUrl = resultSet.getString("media_url");
            return new PostTab(tabId, postId, header, textContent, mediaUrl);
        });
    }

}
