package com.jikji.contentquery.service;

import com.jikji.contentquery.domain.Content;
import java.util.List;

public interface ContentQueryService {

    Content findByContentId(Long contentId);

    List<Content> findByUserId(Long userId);
}
