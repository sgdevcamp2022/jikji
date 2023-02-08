package com.example.neo4j.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.neo4j.dto.user.UserDto;
import com.example.neo4j.service.UserService;

@RestController
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public UserDto createUser(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}

	@GetMapping("/user/{user_id}")
	public UserDto findUser(@PathVariable Long user_id) {
		return userService.findUser(user_id);
	}

	@PutMapping("/user")
	public UserDto updateUser(@RequestBody UserDto userDto) {
		return userService.updateUser(userDto);
	}

	@PostMapping("/user/delete")
	public UserDto deleteUser(@RequestBody UserDto userDto) {
		return userService.deleteUser(userDto);
	}

}
