package com.example.neo4j.entity;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Node
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private Long id;

	@Relationship(type = "FOLLOW", direction = OUTGOING)
	private Set<User> following = new HashSet<>();

	@Relationship(type = "FOLLOW", direction = INCOMING)
	private Set<User> follower = new HashSet<>();

	public User(Long id) {
		this.id = id;
	}
}
