package org.youth.api.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.annotation.valid.ValidPhone;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/all")
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("hello");
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> registUser(@Valid MemberDTO.Regist memberDTO){
		
		memberService.registMember(memberDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("회원을 등록했습니다.")
											.build());
	}
	
	
	
	@PostMapping("/duplicate-phone")
	public ResponseEntity<ResponseDTO> duplcatePhone(@ValidPhone String myPhoneNumber){
		
		memberService.checkAlreadyRegistedPhoneNumber(myPhoneNumber);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("사용할 수 있는 핸드폰 번호입니다.")
											.build());
	}
	
	

	
}
