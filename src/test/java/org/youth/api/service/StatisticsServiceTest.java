package org.youth.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.youth.api.code.SexType;
import org.youth.api.config.persistance.QuerydslConfig;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.StatisticsParam;
import org.youth.api.entity.ContentsEntity;
import org.youth.api.entity.MemberEntity;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles(profiles = "dev")
@SpringBootTest
@Import(QuerydslConfig.class)
@Slf4j
//@Transactional
class StatisticsServiceTest {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	private static List<ContentsEntity> contentsList = new ArrayList<>();
	
	private static List<MemberEntity> memberList = new ArrayList<>();
	
	private static RandomNumber r =  (basic, range) -> (int) (Math.random() * (range)) + basic;

	
	
	@BeforeEach
	void beforeAll(@Autowired ContentsService contentsService,
				   @Autowired MemberService memberService) {
		
		ContentsDTO.Regist enableContentDTO1 = new ContentsDTO.Regist();
		enableContentDTO1.setName("오락실Test");
		enableContentDTO1.setEnableReservation(true);
		enableContentDTO1.setColor("red");
		
		ContentsDTO.Regist enableContentDTO2 = new ContentsDTO.Regist();
		enableContentDTO2.setName("노래방Test");
		enableContentDTO2.setEnableReservation(true);
		enableContentDTO2.setColor("blue");
		
		ContentsDTO.Regist enableContentDTO3 = new ContentsDTO.Regist();
		enableContentDTO3.setName("플스Test");
		enableContentDTO3.setEnableReservation(true);
		enableContentDTO3.setNotice("안 고장났습니다.");
		enableContentDTO3.setColor("blue");
		
		contentsList.add(contentsService.registContents(enableContentDTO1));
		contentsList.add(contentsService.registContents(enableContentDTO2));
		contentsList.add(contentsService.registContents(enableContentDTO3));
		
		
		for(int i = 1; i < 35; i ++) {
			MemberDTO.Regist memberDTO1 = new MemberDTO.Regist();
			memberDTO1.setName("철수" + i);
			memberDTO1.setSex(SexType.M);
			memberDTO1.setBirth(LocalDate.of(r.getNumber(1990, 30),
											 r.getNumber(1, 12),
											 r.getNumber(1, 26)));
			
			memberList.add(memberService.registMember(memberDTO1));
		};
		
		
		
		LocalDateTime start = LocalDateTime.of(2021, 9, 7, 10, 0);
		
		for(int i = 1; i < 1050; i ++) {
			LocalDateTime nextTime = start.plusMinutes(r.getNumber(5, 60));
			ContentsEntity randomContents = contentsList.get(r.getNumber(0, 3));
			MemberEntity randomMember = memberList.get(r.getNumber(0, 34));
			
			ReservationDTO.Regist reservation = createReservation(start,
																   nextTime,
																   randomContents,
																   randomMember);
			
			reservationService.registReservation(reservation);
			
			start = nextTime;
		};
		//회원 1000명
		//회원당 예약 100
		//예약당 1~5명 예약
		
	}
	
	
	@Test
	void test() {
		
		List<Map<String, Object>> result = statisticsService.findMostUsedMember(new StatisticsParam());
		System.out.println(result);
	}
	
	
	
//	public static void main(String[] args) {
//		
//		RandomNumber r =  (basic, range) -> (int) (Math.random() * (range)) + basic;
//		
//		for(int i = 0; i < 500; i++) {
//			System.out.println(r.getNumber(30, 1990));
//		}
//	}

	
	
	public static ReservationDTO.Regist createReservation(LocalDateTime startTime, LocalDateTime endTime, ContentsEntity contents, MemberEntity...members){
		
		
		ReservationDTO.Regist reservation = new ReservationDTO.Regist();
		
		List<MemberDTO.MemberDetails> memberList = Arrays.asList(members)
														 .stream()
														 .map( m -> MemberDTO.MemberDetails.of(m)).collect(Collectors.toList());
		
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setContents(ContentsDTO.Details.of(contents));
		reservation.setMembers(memberList);
		
		return reservation;
	}
	
	
	interface RandomNumber {
		int getNumber(int basic, int range);
	}

}
