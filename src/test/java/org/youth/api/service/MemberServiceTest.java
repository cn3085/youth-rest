//package org.youth.api.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.youth.api.entity.MemberEntity;
//import org.youth.api.repository.MemberRepository;
//
////@DataJpaTest
//class MemberServiceTest {
//
//	@Autowired private MemberRepository memberRepository;
//	
//	@Test
//	public void registMember() {
//		MemberEntity member = MemberEntity.builder()
//										  .name("전주영")
//										  .memo("바보바보")
//										  .build();
//		
//		member = memberRepository.save(member);
//		
//		List<MemberEntity> memberList = memberRepository.findAll();
//		System.out.println(memberList);
//		
//		assertThat(memberList.size()).isEqualTo(1);
//	}
//
//	public void editMember() {
//
//	}
//
//	public void removeMember() {
//
//	}
//
//	public void findAllMember() {
//
//	}
//
//	public void findThisMember() {
//
//	}
//
//}
