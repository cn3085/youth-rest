package org.youth.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.MemberParam;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/members")
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping
	public ResponseEntity<ResponseDTO> getUserList(@PageableDefault(page = 0, size = 10) Pageable page,
												   MemberParam serachParam){
		
		Page<MemberDTO.Details> pageContent = memberService.getMembers(page, serachParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(pageContent)
											.build());
	}
	
	
	
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAllUserList(MemberParam serachParam){
		
		List<MemberDTO.Details> memberList = memberService.getMembers(serachParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(memberList)
											.build());
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> registUser(@RequestBody @Valid MemberDTO.Regist memberDTO){
		
		MemberDTO.MemberDetails member = MemberDTO.MemberDetails.of(memberService.registMember(memberDTO));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(member)
											.message("????????? ??????????????????.")
											.build());
	}
	
	
	
	@PostMapping("/duplicate-phone")
	public ResponseEntity<ResponseDTO> duplcatePhone(
			@NotBlank(message = "????????? ????????? ??????????????????.")
			@Valid
			String myPhoneNumber){ //TODO: ??????????????? ?????? ?????? ???????????????
		
		memberService.checkAlreadyRegistedPhoneNumber(myPhoneNumber);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("????????? ??? ?????? ????????? ???????????????.")
											.build());
	}
	
	
	
	@PostMapping("/duplicate-name")
	public ResponseEntity<ResponseDTO> duplcateName(
			@NotBlank(message = "????????? ??????????????????.")
			@Valid
			String name){ 
		
		memberService.checkAlreadyRegistedMemberName(name);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("???????????? ?????? ?????????????????????.")
											.build());
	}
	
	
	
	@GetMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> getThisMember(@PathVariable Long memberId){
		
		MemberDTO.Details memberDTO = MemberDTO.Details.of(memberService.getMemberDetails(memberId));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.data(memberDTO)
											.code(ResponseCode.SUCC)
											.build());
	}
	
	
	
	@DeleteMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> deleteMember(@PathVariable Long memberId){
		
		memberService.deleteMember(memberId);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("?????? ????????? ??????????????????.")
											.build());
	}
	
	
	
	@PutMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> updateMember(@PathVariable Long memberId,
													@RequestBody MemberDTO.Details memberDTO){
		
		memberService.updateMember(memberId, memberDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("?????? ????????? ??????????????????.")
											.build());
	}
	
	

	
}
