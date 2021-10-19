package org.youth.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.youth.api.code.ReservationState;
import org.youth.api.config.persistance.QuerydslConfig;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.entity.ContentsEntity;
import org.youth.api.entity.MemberEntity;
import org.youth.api.entity.ReservationEntity;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;
import org.youth.api.exception.reservation.OverTimeUseMemberException;
import org.youth.api.exception.reservation.ReservationRestrictContentsException;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles(profiles = "dev")
@SpringBootTest
@Import(QuerydslConfig.class)
@Slf4j
@Transactional
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;
	
	private static ContentsEntity enableContent1;
	private static ContentsEntity enableContent2;
	
	private static ContentsEntity disableContent;
	
	private static MemberEntity member1;
	private static MemberEntity member2;
	
	@BeforeEach
	void beforeAll(@Autowired ContentsService contentsService,
								 @Autowired MemberService memberService) {
		
		ContentsDTO.Regist enableContentDTO1 = new ContentsDTO.Regist();
		enableContentDTO1.setName("오락실");
		enableContentDTO1.setEnableReservation(true);
		enableContentDTO1.setColor("red");
		
		ContentsDTO.Regist enableContentDTO2 = new ContentsDTO.Regist();
		enableContentDTO2.setName("노래방");
		enableContentDTO2.setEnableReservation(true);
		enableContentDTO2.setColor("blue");
		
		ContentsDTO.Regist disableContentDTO = new ContentsDTO.Regist();
		disableContentDTO.setName("플스(고장)");
		disableContentDTO.setEnableReservation(false);
		disableContentDTO.setNotice("고장났습니다.");;
		disableContentDTO.setColor("blue");
		
		enableContent1 = contentsService.registContents(enableContentDTO1);
		enableContent2 = contentsService.registContents(enableContentDTO2);
		
		disableContent = contentsService.registContents(disableContentDTO);
		
		MemberDTO.Regist memberDTO1 = new MemberDTO.Regist();
		memberDTO1.setName("철수");
		memberDTO1.setBirth(LocalDate.of(1992, 12, 9));
		memberDTO1.setMyPhoneNumber("010-9999-9999");
		
		MemberDTO.Regist memberDTO2 = new MemberDTO.Regist();
		memberDTO2.setName("영희");
		memberDTO2.setBirth(LocalDate.of(2003, 12, 9));
		memberDTO2.setMyPhoneNumber("010-8888-8888");
		
		member1 = memberService.registMember(memberDTO1);
		member2 = memberService.registMember(memberDTO2);
		
	}
	
	
	
	//1. 예약 시간이 겹치는 경우
	//1.1. 등록된 예약과 예약 시간이 완전히 겹침
	@Test
	void _1_1등록된_예약과_예약시간이_완전히_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1); 

		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member2);

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
	void _1_2등록된_예약종료시간과_예약시작_시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 20),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	enableContent1,
																	member2);

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
	void _1_3등록된_예약시작시간과_예약종료시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 50),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent1,
																	member2);

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
	void _1_4등록된_예약시간_안에_포함되어_있음() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 10),
																	LocalDateTime.of(2021, 9, 7, 10, 20),
																	enableContent1,
																	member2);

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
	void _1_5등록된_예약시간을_포함함() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 0),
																	LocalDateTime.of(2021, 9, 7, 11, 0),
																	enableContent1,
																	member2);

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
	void _1_6시작시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent1,
																	member2);
		
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
	void _1_6O_다른_컨텐츠와_시간이_겹침() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent2,
																	member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	
	
	//2. 다른 예약을 이용중인 사용자가 있음
	//2.1. 다른 예약을 이용 중인 사용자가 포함됨 -1명
	@Test
	void _2_1O_다른_예약을_이용_중인_사용자가_포함됨_1명() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	enableContent2,
																	member1, member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	@Test
	void _2_1X_다른_예약을_이용_중인_사용자가_포함됨_1명() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
																	LocalDateTime.of(2021, 9, 7, 10, 40),
																	enableContent2,
																	member1, member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		ContainsAnotherReservationException exception = assertThrows(ContainsAnotherReservationException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
		
		assertThat(exception.getBannedMemberList().size()).isEqualTo(1);
		assertThat(exception.getBannedMemberList().get(0).getMemberId()).isEqualTo(member1.getMemberId());
		
	}
	
	
	
	//2.2. 다른 예약을 이용 중인 사용자가 포함됨 -전원
	@Test
	void _2_2다른_예약을_이용_중인_사용자가_포함됨_전원() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent2,
																	member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	//3. 예약 시간이 겹치고 다른 예약도 이용중임
	@Test
	void _3_1예약_시간이_겹치고_다른_예약도_이용중임() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent1,
																	member1, member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		//then
		assertThrows(DoubleBookingException.class, () -> {
			reservationService.registReservation(secondReservation);
		});
	}
	
	
	
	//3.2. 예약이 제한된 컨텐츠에 예약하는 경우
	@Test
	void _3_2예약이_제한된_컨텐츠에_예약하는_경우() {
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 30),
																	disableContent,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																	LocalDateTime.of(2021, 9, 7, 10, 10),
																	enableContent1,
																	member1, member2);
		
		//when
		assertThrows(ReservationRestrictContentsException.class, () -> {
			reservationService.registReservation(firstReservation);
		});
		
		//then
		reservationService.registReservation(secondReservation);
	}
	
	
	
	@Test
	void _3_3예약제한시간을_초과한_예약하는_경우() {
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 50)),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 52)),
																	enableContent1,
																	member1, member2);
		
		//when
		reservationService.registReservation(firstReservation);
		
		OverTimeUseMemberException ex = assertThrows(OverTimeUseMemberException.class, () -> {
											reservationService.registReservation(secondReservation);
										});
		
		//then
		assertThat(ex.getOverTimeMembers().size()).isEqualTo(1);
		assertThat(ex.getOverTimeMembers().get(0).getMemberId()).isEqualTo(member1.getMemberId());
	}
	
	
	
	@Test
	void _3_4취소된예약과_같은시간에_예약하는경우_예약성공() {
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 50)),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)),
																	enableContent1,
																	member1, member2);
		
		//when
		ReservationEntity first = reservationService.registReservation(firstReservation);
		first.cancel();
		
		
		//then
		ReservationEntity second = reservationService.registReservation(secondReservation);
		
		assertThat(first.getState()).isEqualTo(ReservationState.CANCEL);
		assertThat(second.getState()).isEqualTo(ReservationState.OK);
	}
	
	
	
	@Test
	void _3_5하루이용시간에_취소된예약시간은_카운트_안함() {
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist cancelReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
																	enableContent1,
																	member1);
		
		ReservationDTO.Regist fourthReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
																	enableContent1,
																	member1);
		
		
		ReservationDTO.Regist fiveReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 40)),
																	enableContent1,
																	member1);
		
		//when
		ReservationEntity first = reservationService.registReservation(firstReservation);
		ReservationEntity second = reservationService.registReservation(secondReservation);
		ReservationEntity third = reservationService.registReservation(cancelReservation);
		third.cancel();
		
		ReservationEntity fourth = reservationService.registReservation(fourthReservation);
		
		
		
		//then
		OverTimeUseMemberException ex = assertThrows(OverTimeUseMemberException.class, () -> {
			reservationService.registReservation(fiveReservation);
		});

		assertThat(ex.getOverTimeMembers()).isNotNull().isNotEmpty();
		assertThat(ex.getOverTimeMembers().get(0).getUsedMinute()).isEqualTo(90);
	}
	
	
	
	//4.예약변경
	//4.1
	@Test
	void _4_1예약변경_성공() {
		
		//given
		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
																   LocalDateTime.of(2021, 9, 7, 10, 30),
																   enableContent1,
																   member1);
		ReservationEntity first = reservationService.registReservation(firstReservation);
		
		//when
		ReservationDTO.Details updateReservation = new ReservationDTO.Details();
		updateReservation.setReservationId(first.getReservationId());
		updateReservation.setStartTime(LocalDateTime.of(2021, 9, 7, 11, 0));
		updateReservation.setEndTime(LocalDateTime.of(2021, 9, 7, 11, 30));
		
		List<MemberDTO.MemberDetails> memberList = firstReservation.getMembers();
		memberList.clear();
		
		MemberDTO.MemberDetails newMember = MemberDTO.MemberDetails.of(member2);
		memberList.add(newMember);
		
		updateReservation.setMembers(memberList);
		updateReservation.setContents(firstReservation.getContents());
		
		reservationService.updateReservation(updateReservation);
		
		//then
		ReservationEntity reservation = reservationService.getReservationDetails(first.getReservationId());
		
		assertThat(reservation.getStartTime()).isEqualTo(updateReservation.getStartTime());
		assertThat(reservation.getEndTime()).isEqualTo(updateReservation.getEndTime());
		assertThat(reservation.getMembers()).isNotEmpty();
		assertThat(reservation.getMembers().get(0)).isEqualTo(member2);
		assertThat(reservation.getContents()).isNotNull();
		assertThat(reservation.getContents()).isEqualTo(first.getContents());
		
	}
	
	
	
	
	
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

}
