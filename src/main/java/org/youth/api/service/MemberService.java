package org.youth.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.MemberDTO.Details;
import org.youth.api.entity.MemberEntity;
import org.youth.api.exception.member.AlreadyRegistedMemberException;
import org.youth.api.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService{
	
	private final MemberRepository memberRepository; 
	 
	
	@Transactional(rollbackFor = Exception.class)
	public void registMember(MemberDTO.Regist memberDTO) {
		
		checkAlreadyRegistedPhoneNumber(memberDTO.getMyPhoneNumber());
		MemberEntity member = memberDTO.toEntity();
		
		memberRepository.save(member);
	}
	
	
	
	@Transactional(readOnly = true)
	public void checkAlreadyRegistedPhoneNumber(String phoneNumber) {
		Optional<MemberEntity> member = memberRepository.findByMyPhoneNumber(phoneNumber);
		
		if(member.isPresent()) {
			throw new AlreadyRegistedMemberException("myPhoneNumber", phoneNumber);
		}
	}
	

	
	@Transactional(readOnly = true)
	public MemberEntity getMemberDetails(long memberId) {
		return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다. " + memberId));
	}

	

	@Transactional(rollbackFor = Exception.class)
	public void deleteMember(long memberId) {
		MemberEntity member = getMemberDetails(memberId);
		memberRepository.delete(member);
	}


	
	@Transactional(rollbackFor = Exception.class)
	public void updateMember(long memberId, Details memberDTO) {
		
		MemberEntity member = getMemberDetails(memberId);
		member.updateDetails(memberDTO);
	}



	

}
