package org.youth.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.youth.api.config.persistance.QuerydslConfig;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles(profiles = "local")
@SpringBootTest
@Import(QuerydslConfig.class)
@Slf4j
@Transactional
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;  
	
	//1. 예약 시간이 겹치는 경우
	//1.1. 등록된 예약과 예약 시간이 완전히 겹침
	@Test
	void 등록된_예약과_예약시간이_완전히_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   1,
																   new Long[]{1L});

		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});

		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.2. 등록된 예약 종료시간과 예약 시작 시간이 겹침
	@Test
	void 등록된_예약종료시간과_예약시작_시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   1,
																   new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 20),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	1,
																	new Long[]{1L});

		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.3. 등록된 예약의 시작시간과 예약 종료 시간이 겹침
	@Test
	void 등록된_예약시작시간과_예약종료시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   1,
																   new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 50),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	1,
																	new Long[]{1L});

		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.4. 등록된 예약 시간 안에 포함되어 있음
	@Test
	void 등록된_예약시간_안에_포함되어_있음() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   1,
																   new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 10),
																	LocalDateTime.of(2021, 9, 7, 10, 20),
																	1,
																	new Long[]{1L});

		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.5. 등록된 예약 시간을 포함함
	@Test
	void 등록된_예약시간을_포함함() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   1,
																   new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 0),
																	LocalDateTime.of(2021, 9, 7, 11, 0),
																	1,
																	new Long[]{1L});

		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.6. 시작시간이 겹침
	@Test
	void 시작시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	1,
																	new Long[]{1L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		log.info(exception.getMessage());
		
	}
	
	
	
	//1.6. 다른 컨텐츠와 시간이 겹침
	@Test
	void O_다른_컨텐츠와_시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	2,
																	new Long[]{2L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	
	
	//2. 다른 예약을 이용중인 사용자가 있음
	//2.1. 다른 예약을 이용 중인 사용자가 포함됨 -1명
	@Test
	void O_다른_예약을_이용_중인_사용자가_포함됨_1명() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	2,
																	new Long[]{1L, 2L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	@Test
	void X_다른_예약을_이용_중인_사용자가_포함됨_1명() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	2,
																	new Long[]{1L, 2L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		ContainsAnotherReservationException exception = assertThrows(ContainsAnotherReservationException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		assertThat(exception.getBannedMemberList().size()).isEqualTo(1);
		assertThat(exception.getBannedMemberList().get(0).getMemberId()).isEqualTo(1L);
		
	}
	
	
	
	//2.2. 다른 예약을 이용 중인 사용자가 포함됨 -전원
	@Test
	void 다른_예약을_이용_중인_사용자가_포함됨_전원() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	2,
																	new Long[]{2L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	//3. 예약 시간이 겹치고 다른 예약도 이용중임
	@Test
	void 예약_시간이_겹치고_다른_예약도_이용중임() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	1,
																	new Long[]{1L});
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	2,
																	new Long[]{2L});
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	
	public static ReservationDTO.Regist createReservation(LocalDateTime startTime, LocalDateTime endTime, long contentsId, Long[] memberIds){
		
		ContentsDTO.Details contents = new ContentsDTO.Details();
		contents.setContentsId(contentsId);
		
		List<MemberDTO.Details> memberList = Arrays.asList(memberIds).stream().map( id -> {
			MemberDTO.Details member = new MemberDTO.Details();
			member.setMemberId(id);
			return member;
		}).collect(Collectors.toList());
		
		
		ReservationDTO.Regist reservation = new ReservationDTO.Regist();
		
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setContents(contents);
		reservation.setMembers(memberList);
		
		return reservation;
	}

}
