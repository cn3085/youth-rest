package org.youth.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.MemberDTO.Regist;
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
	
	
	public void checkAlreadyRegistedPhoneNumber(String phoneNumber) {
		Optional<MemberEntity> member = memberRepository.findByMyPhoneNumber(phoneNumber);
		
		if(member.isPresent()) {
			throw new AlreadyRegistedMemberException("myPhoneNumber", phoneNumber);
		}
	}


	public void editMember() {
		
	}
	
	public void removeMember() {
		
	}
	
	public void findAllMember() {
		
	}
	
	public void findThisMember() {
		
	}

}
