package com.jikji.contentcommand.service;

import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;

public interface ContentCommandService {

    Long save(final ContentCreateRequest request);

    void update(final Long contentId, final ContentUpdateRequest request);

    void delete(final Long contentId);
}
