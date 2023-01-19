package com.jikji.contentcommand.service;

import com.jikji.contentcommand.dto.request.ContentCreateRequest;

public interface ContentCommandService {

    Long save(final ContentCreateRequest request);
}
