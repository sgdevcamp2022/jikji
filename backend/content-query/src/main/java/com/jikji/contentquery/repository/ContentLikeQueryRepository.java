package com.jikji.contentquery.repository;

import com.jikji.contentquery.domain.ContentLike;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentLikeQueryRepository extends MongoRepository<ContentLike, String> {
}
