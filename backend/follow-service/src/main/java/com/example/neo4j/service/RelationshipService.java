package com.example.neo4j.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.neo4j.dto.relationship.FollowDto;
import com.example.neo4j.dto.user.UserDto;
import com.example.neo4j.entity.User;
import com.example.neo4j.repository.RelationshipRepository;

@Service
public class RelationshipService {
	final RelationshipRepository relationshipRepository;

	private User userDtoToUser(UserDto userDto) {
		return new User(
			userDto.getId()
		);
	}

	private UserDto userToUserDto(User user) {
		return new UserDto(
			user.getId()
		);
	}

	public RelationshipService(RelationshipRepository relationshipRepository) {
		this.relationshipRepository = relationshipRepository;
	}

	public FollowDto createFollowing(FollowDto followDto) {
		relationshipRepository.createFollowing(followDto.getFollowing_id(), followDto.getFollowed_id());
		return followDto;
	}

	public FollowDto deleteFollowing(FollowDto followDto) {
		relationshipRepository.deleteFollowing(followDto.getFollowing_id(), followDto.getFollowed_id());
		return followDto;
	}

	public List<UserDto> findUsersFollowing(Long user_id) {
		List<User> userList = relationshipRepository.findUsersFollowing(user_id);
		return userList.stream().map(o -> new UserDto(o.getId())).collect(Collectors.toList());
	}

	public List<UserDto> findUsersFollower(Long user_id) {
		List<User> userList = relationshipRepository.findUsersFollower(user_id);
		return userList.stream().map(o -> new UserDto(o.getId())).collect(Collectors.toList());
	}

	public Integer countUsersFollowing(Long user_id) {
		return relationshipRepository.countUsersFollowing(user_id);
	}

	public Integer countUsersFollower(Long user_id) {
		return relationshipRepository.countUsersFollower(user_id);
	}

}
