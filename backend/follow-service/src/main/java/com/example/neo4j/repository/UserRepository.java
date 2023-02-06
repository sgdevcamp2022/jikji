package com.example.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.neo4j.entity.User;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

	@Query("match (a:User) "
		+ "where a.id = $user_id "
		+ "detach delete a ")
	Void deleteUser(@Param("user_id") Long user_id);

	User findFirstById(@Param("id") Long user_id);

}
