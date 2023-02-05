package com.jikji.contentquery.repository;

import com.jikji.contentquery.domain.Bookmark;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookmarkQueryRepository extends MongoRepository<Bookmark, String> {

    Optional<Bookmark> findByBookmarkId(Long BookmarkId);
}
