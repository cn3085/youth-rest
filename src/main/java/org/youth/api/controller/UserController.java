package org.youth.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.dto.UserDTO;
import org.youth.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/join")
	public ResponseEntity<Long> join(@RequestBody UserDTO.UserRequest userRequest) {
		Long userId = userService.joinUser(userRequest);
		return ResponseEntity.ok(userId);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO.UserRequest userRequest) {
		String token = userService.login(userRequest);
		return ResponseEntity.ok(token);
	}
	
}
