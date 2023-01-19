package com.jikji.contentcommand.repository;

import com.jikji.contentcommand.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentCommandRepository extends JpaRepository<Content, Long> {
}
