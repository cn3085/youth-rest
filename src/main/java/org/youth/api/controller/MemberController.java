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
											.message("회원을 등록했습니다.")
											.build());
	}
	
	
	
	@PostMapping("/duplicate-phone")
	public ResponseEntity<ResponseDTO> duplcatePhone(
			@NotBlank(message = "핸드폰 번호는 필수값입니다.")
//			@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "핸드폰 번호가 형식에 맞지 않습니다. (xxx-xxxx-xxxx)")
			@Valid
			String myPhoneNumber){ //TODO: 어노테이션 두개 내포 가능하도록
		
		memberService.checkAlreadyRegistedPhoneNumber(myPhoneNumber);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("사용할 수 있는 핸드폰 번호입니다.")
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
											.message("회원 정보를 삭제했습니다.")
											.build());
	}
	
	
	
	@PutMapping("/{memberId}")
	public ResponseEntity<ResponseDTO> updateMember(@PathVariable Long memberId,
													@RequestBody MemberDTO.Details memberDTO){
		
		memberService.updateMember(memberId, memberDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("회원 정보를 수정했습니다.")
											.build());
	}
	
	

	
}
