package org.youth.api.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.MemberDTO.Details;
import org.youth.api.dto.MemberParam;
import org.youth.api.entity.MemberEntity;
import org.youth.api.exception.member.AlreadyRegistedMemberException;
import org.youth.api.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService{
	
	private final MemberRepository memberRepository; 
	 
	
	@Transactional(rollbackFor = Exception.class)
	public MemberEntity registMember(MemberDTO.Regist memberDTO) {
		
		//checkAlreadyRegistedPhoneNumber(memberDTO.getMyPhoneNumber());
		MemberEntity member = memberDTO.toEntity();
		
		return memberRepository.save(member);
	}
	
	
	
	@Transactional(readOnly = true)
	public void checkAlreadyRegistedPhoneNumber(String phoneNumber) {
		List<MemberEntity> member = memberRepository.findByMyPhoneNumber(phoneNumber);
		
		if(!member.isEmpty()) {
			throw new AlreadyRegistedMemberException("myPhoneNumber", phoneNumber);
		}
	}
	
	
	
	@Transactional(readOnly = true)
	public void checkAlreadyRegistedMemberName(@Valid String name) {
		
		if(memberRepository.existsByName(name)) {
			throw new AlreadyRegistedMemberException("name", name);
		}
	}
	
	
	
	@Transactional(readOnly = true)
	public void checkAlreadyRegistedPhoneNumber(String phoneNumber, MemberEntity me) {
		List<MemberEntity> member = memberRepository.findByMyPhoneNumber(phoneNumber);
		
		if(member.stream().filter(m -> !m.equals(me)).count() > 0) {
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
		//checkAlreadyRegistedPhoneNumber(memberDTO.getMyPhoneNumber(), member);
		member.updateDetails(memberDTO);
	}



	@Transactional(readOnly = true)
	public Page<MemberDTO.Details> getMembers(Pageable page, MemberParam memberParam) {
		
		return memberRepository.searchAll(page, memberParam).map(MemberDTO.Details::of);
	}



	@Transactional(readOnly = true)
	public List<MemberDTO.Details> getMembers(MemberParam searchParam) {
		
		return memberRepository.searchAll(searchParam).stream().map(MemberDTO.Details::of).collect(Collectors.toList()); 
	}

	

}
