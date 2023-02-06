package com.example.neo4j.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.neo4j.dto.relationship.FollowDto;
import com.example.neo4j.dto.user.UserDto;
import com.example.neo4j.service.RelationshipService;

@RestController
public class RelationshipController {

	final RelationshipService relationshipService;

	public RelationshipController(RelationshipService relationshipService) {
		this.relationshipService = relationshipService;
	}

	@PostMapping("/follow/create")
	public FollowDto createFollow(@RequestBody FollowDto followDto) {
		return relationshipService.createFollowing(followDto);
	}

	@GetMapping("/user/{user_id}/following")
	public List<UserDto> findUsersFollowing(@PathVariable Long user_id) {
		return relationshipService.findUsersFollowing(user_id);
	}

	@GetMapping("/user/{user_id}/follower")
	public List<UserDto> findUsersFollower(@PathVariable Long user_id) {
		return relationshipService.findUsersFollower(user_id);
	}

	@GetMapping("/user/{user_id}/following/count")
	public Integer countUsersFollowing(@PathVariable Long user_id) {
		return relationshipService.countUsersFollowing(user_id);
	}

	@GetMapping("/user/{user_id}/follower/count")
	public Integer countUsersFollower(@PathVariable Long user_id) {
		return relationshipService.countUsersFollower(user_id);
	}

	@PostMapping("/follow/delete")
	public FollowDto deleteFollow(@RequestBody FollowDto followDto) {
		return relationshipService.deleteFollowing(followDto);
	}

}
