package com.example.neo4j.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.neo4j.entity.User;

@Repository
public interface RelationshipRepository extends Neo4jRepository<User, Long> {

	@Query("match (a:User) "
		+ "where a.id = $following_id "
		+ "match (b:User) "
		+ "where b.id = $followed_id "
		+ "create (a)-[:FOLLOW]->(b) "
		+ "return a")
	User createFollowing(@Param("following_id") Long following_id, @Param("followed_id") Long followed_id);

	@Query("match (a:User) "
		+ "where a.id = $following_id "
		+ "match (b:User) "
		+ "where b.id = $followed_id "
		+ "match (a)-[f:FOLLOW]->(b) "
		+ "delete f "
		+ "return a")
	User deleteFollowing(@Param("following_id") Long following_id, @Param("followed_id") Long followed_id);

	@Query("match(a:User)-[:FOLLOW]->(b:User) "
		+ "where a.id = $user_id return b")
	List<User> findUsersFollowing(@Param("user_id") Long user_id);

	@Query("match(a:User)-[:FOLLOW]->(b:User) "
		+ "where b.id = $user_id return a")
	List<User> findUsersFollower(@Param("user_id") Long user_id);

	@Query("match(a:User) "
		+ "where a.id = $user_id "
		+ "return COUNT { (a)-[:FOLLOW]->() }")
	Integer countUsersFollowing(@Param("user_id") Long user_id);

	@Query("match(b:User) "
		+ "where b.id = $user_id "
		+ "return COUNT { ()-[:FOLLOW]->(b) }")
	Integer countUsersFollower(@Param("user_id") Long user_id);

}
