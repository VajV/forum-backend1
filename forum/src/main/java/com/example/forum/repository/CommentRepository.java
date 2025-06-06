package com.example.forum.repository;

import com.example.forum.model.Comment;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    Optional<Comment> findById(Long id);
}
