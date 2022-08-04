package com.codingoutreach.clubservice.repository;

import com.codingoutreach.clubservice.controllers.DO.PostCreationRequest;
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

    private final String INSERT_POST = "INSERT INTO post VALUES (?,?,?,?,?)";



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> getAllPosts(UUID clubId) {
        List<Post> temp = jdbcTemplate.query(GET_ALL_POSTS, new Object[] {clubId}, mapPost());
        return temp;
    }

    public int insertPost(PostCreationRequest postCreationRequest) {
        return jdbcTemplate.update(INSERT_POST, UUID.randomUUID(), postCreationRequest.getClub_id(), postCreationRequest.getTitle(), postCreationRequest.getText_content(), postCreationRequest.getMedia_url());
    }



    public RowMapper<Post> mapPost() {
        return ((resultSet, i) -> {
            UUID post_id = UUID.fromString(resultSet.getString("post_id"));
            UUID sender = UUID.fromString(resultSet.getString("sender"));
            String title = resultSet.getString("title");
            String textContent = resultSet.getString("text_content");
            String mediaURL = resultSet.getString("media_url");
            return new Post(post_id, sender, title, textContent, mediaURL);
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
