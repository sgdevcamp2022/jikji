package com.jikji.contentcommand.repository;

import com.jikji.contentcommand.domain.ContentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommandRepository extends JpaRepository<ContentLike, Long> {

    Optional<ContentLike> findByUserIdAndContentId(Long userId, Long contentId);

    Boolean existsByUserIdAndContentId(Long userId, Long contentId);
}
