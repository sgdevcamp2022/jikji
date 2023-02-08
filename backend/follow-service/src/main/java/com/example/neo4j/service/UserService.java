package com.example.neo4j.service;

import org.springframework.stereotype.Service;

import com.example.neo4j.dto.user.UserDto;
import com.example.neo4j.entity.User;
import com.example.neo4j.repository.UserRepository;

@Service
public class UserService {
	final UserRepository userRepository;

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

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDto saveUser(UserDto userDto) {
		User save = userRepository.save(userDtoToUser(userDto));
		return userToUserDto(save);
	}

	public UserDto updateUser(UserDto userDto) {
		User firstById = userRepository.findFirstById(userDto.getId());
		if (firstById != null) {
			User save = userRepository.save(userDtoToUser(userDto));
			return userToUserDto(save);
		}
		return null;
	}

	public UserDto deleteUser(UserDto userDto) {
		userRepository.deleteUser(userDto.getId());
		return userDto;
	}

	public UserDto findUser(Long user_id) {
		User firstById = userRepository.findFirstById(user_id);
		return userToUserDto(firstById);
	}

}
