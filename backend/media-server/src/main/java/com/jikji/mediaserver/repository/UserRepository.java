package com.jikji.mediaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jikji.mediaserver.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
