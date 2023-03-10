package com.jikji.contentcommand.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jikji.contentcommand.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Integer countByPostId(Long postId);
}
