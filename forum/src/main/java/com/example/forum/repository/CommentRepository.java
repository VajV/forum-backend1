package com.example.forum.repository;

import com.example.forum.model.Comment;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :postId")
    List<Comment> findByPostIdWithPost(@Param("postId") Long postId);
    Optional<Comment> findById(Long id);
}
