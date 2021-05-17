package org.youth.api.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.youth.api.config.JwtTokenProvider;
import org.youth.api.dto.UserDTO;
import org.youth.api.dto.UserDTO.UserRequest;
import org.youth.api.entity.UserEntity;
import org.youth.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;

	
	public long joinUser(UserDTO.UserRequest userRequest) {
		
		final String rawPassword = userRequest.getPassword();
		final String encodedPassword = passwordEncoder.encode(rawPassword);
		
		UserEntity userEntity = UserEntity.builder()
											.userId(userRequest.getUserId())
											.password(encodedPassword)
											.roles(Collections.singletonList("ROLE_ADMIN"))
											.build();
		
		return userRepository.save(userEntity).getId();
	}

	
	public String login(UserDTO.UserRequest userRequest) {
		
		String userId = userRequest.getUserId();
		String rawPassword = userRequest.getPassword();
		
		UserEntity savedUser = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. " + userId));
		if(!passwordEncoder.matches(rawPassword, savedUser.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRoles());
	}
	
	
	
	
}
