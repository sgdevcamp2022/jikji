package com.jikji.mediaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jikji.mediaserver.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
