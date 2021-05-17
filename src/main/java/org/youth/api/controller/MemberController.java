package org.youth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member")
public class MemberController {
	
	@GetMapping("/all")
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("hello");
	}
	
	

	
}
