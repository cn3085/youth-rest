//package org.youth.api.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertThrows;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.transaction.Transactional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.youth.api.code.ReservationState;
//import org.youth.api.config.persistance.QuerydslConfig;
//import org.youth.api.dto.ContentsDTO;
//import org.youth.api.dto.MemberDTO;
//import org.youth.api.dto.ReservationDTO;
//import org.youth.api.entity.ContentsEntity;
//import org.youth.api.entity.MemberEntity;
//import org.youth.api.entity.ReservationEntity;
//import org.youth.api.exception.reservation.ContainsAnotherReservationException;
//import org.youth.api.exception.reservation.DoubleBookingException;
//import org.youth.api.exception.reservation.OverTimeUseMemberException;
//import org.youth.api.exception.reservation.ReservationRestrictContentsException;
//
//import lombok.extern.slf4j.Slf4j;
//
//@ActiveProfiles(profiles = "dev")
//@SpringBootTest
//@Import(QuerydslConfig.class)
//@Slf4j
//@Transactional
//class ReservationServiceTest {
//
//	@Autowired
//	private ReservationService reservationService;
//	
//	private static ContentsEntity enableContent1;
//	private static ContentsEntity enableContent2;
//	
//	private static ContentsEntity disableContent;
//	
//	private static MemberEntity member1;
//	private static MemberEntity member2;
//	
//	@BeforeEach
//	void beforeAll(@Autowired ContentsService contentsService,
//								 @Autowired MemberService memberService) {
//		
//		ContentsDTO.Regist enableContentDTO1 = new ContentsDTO.Regist();
//		enableContentDTO1.setName("?????????");
//		enableContentDTO1.setEnableReservation(true);
//		enableContentDTO1.setColor("red");
//		
//		ContentsDTO.Regist enableContentDTO2 = new ContentsDTO.Regist();
//		enableContentDTO2.setName("?????????");
//		enableContentDTO2.setEnableReservation(true);
//		enableContentDTO2.setColor("blue");
//		
//		ContentsDTO.Regist disableContentDTO = new ContentsDTO.Regist();
//		disableContentDTO.setName("??????(??????)");
//		disableContentDTO.setEnableReservation(false);
//		disableContentDTO.setNotice("??????????????????.");;
//		disableContentDTO.setColor("blue");
//		
//		enableContent1 = contentsService.registContents(enableContentDTO1);
//		enableContent2 = contentsService.registContents(enableContentDTO2);
//		
//		disableContent = contentsService.registContents(disableContentDTO);
//		
//		MemberDTO.Regist memberDTO1 = new MemberDTO.Regist();
//		memberDTO1.setName("??????");
//		memberDTO1.setBirth(LocalDate.of(1992, 12, 9));
//		memberDTO1.setMyPhoneNumber("010-9999-9999");
//		
//		MemberDTO.Regist memberDTO2 = new MemberDTO.Regist();
//		memberDTO2.setName("??????");
//		memberDTO2.setBirth(LocalDate.of(2003, 12, 9));
//		memberDTO2.setMyPhoneNumber("010-8888-8888");
//		
//		member1 = memberService.registMember(memberDTO1);
//		member2 = memberService.registMember(memberDTO2);
//		
//	}
//	
//	
//	
//	//1. ?????? ????????? ????????? ??????
//	//1.1. ????????? ????????? ?????? ????????? ????????? ??????
//	@Test
//	void _1_1?????????_?????????_???????????????_?????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1); 
//
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member2);
//
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.2. ????????? ?????? ??????????????? ?????? ?????? ????????? ??????
//	@Test
//	void _1_2?????????_?????????????????????_????????????_?????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 20),
//																	LocalDateTime.of(2021, 9, 7, 10, 40),
//																	enableContent1,
//																	member2);
//
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.3. ????????? ????????? ??????????????? ?????? ?????? ????????? ??????
//	@Test
//	void _1_3?????????_?????????????????????_?????????????????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 50),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent1,
//																	member2);
//
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.4. ????????? ?????? ?????? ?????? ???????????? ??????
//	@Test
//	void _1_4?????????_????????????_??????_????????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 10),
//																	LocalDateTime.of(2021, 9, 7, 10, 20),
//																	enableContent1,
//																	member2);
//
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.5. ????????? ?????? ????????? ?????????
//	@Test
//	void _1_5?????????_???????????????_?????????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 9, 0),
//																	LocalDateTime.of(2021, 9, 7, 11, 0),
//																	enableContent1,
//																	member2);
//
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.6. ??????????????? ??????
//	@Test
//	void _1_6???????????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent1,
//																	member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		DoubleBookingException exception = assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		log.info(exception.getMessage());
//		
//	}
//	
//	
//	
//	//1.6. ?????? ???????????? ????????? ??????
//	@Test
//	void _1_6O_??????_????????????_?????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent2,
//																	member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		reservationService.registReservation(secondReservation);
//	}
//	
//	
//	
//	
//	
//	//2. ?????? ????????? ???????????? ???????????? ??????
//	//2.1. ?????? ????????? ?????? ?????? ???????????? ????????? -1???
//	@Test
//	void _2_1O_??????_?????????_??????_??????_????????????_?????????_1???() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
//																	LocalDateTime.of(2021, 9, 7, 10, 40),
//																	enableContent2,
//																	member1, member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		reservationService.registReservation(secondReservation);
//	}
//	
//	
//	
//	@Test
//	void _2_1X_??????_?????????_??????_??????_????????????_?????????_1???() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
//																	LocalDateTime.of(2021, 9, 7, 10, 40),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 30),
//																	LocalDateTime.of(2021, 9, 7, 10, 40),
//																	enableContent2,
//																	member1, member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		ContainsAnotherReservationException exception = assertThrows(ContainsAnotherReservationException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//		
//		assertThat(exception.getBannedMemberList().size()).isEqualTo(1);
//		assertThat(exception.getBannedMemberList().get(0).getMemberId()).isEqualTo(member1.getMemberId());
//		
//	}
//	
//	
//	
//	//2.2. ?????? ????????? ?????? ?????? ???????????? ????????? -??????
//	@Test
//	void _2_2??????_?????????_??????_??????_????????????_?????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent2,
//																	member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		reservationService.registReservation(secondReservation);
//	}
//	
//	
//	
//	//3. ?????? ????????? ????????? ?????? ????????? ????????????
//	@Test
//	void _3_1??????_?????????_?????????_??????_?????????_????????????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent1,
//																	member1, member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		//then
//		assertThrows(DoubleBookingException.class, () -> {
//			reservationService.registReservation(secondReservation);
//		});
//	}
//	
//	
//	
//	//3.2. ????????? ????????? ???????????? ???????????? ??????
//	@Test
//	void _3_2?????????_?????????_????????????_????????????_??????() {
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 30),
//																	disableContent,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																	LocalDateTime.of(2021, 9, 7, 10, 10),
//																	enableContent1,
//																	member1, member2);
//		
//		//when
//		assertThrows(ReservationRestrictContentsException.class, () -> {
//			reservationService.registReservation(firstReservation);
//		});
//		
//		//then
//		reservationService.registReservation(secondReservation);
//	}
//	
//	
//	
//	@Test
//	void _3_3?????????????????????_?????????_????????????_??????() {
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 50)),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 52)),
//																	enableContent1,
//																	member1, member2);
//		
//		//when
//		reservationService.registReservation(firstReservation);
//		
//		OverTimeUseMemberException ex = assertThrows(OverTimeUseMemberException.class, () -> {
//											reservationService.registReservation(secondReservation);
//										});
//		
//		//then
//		assertThat(ex.getOverTimeMembers().size()).isEqualTo(1);
//		assertThat(ex.getOverTimeMembers().get(0).getMemberId()).isEqualTo(member1.getMemberId());
//	}
//	
//	
//	
//	@Test
//	void _3_4??????????????????_???????????????_??????????????????_????????????() {
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 50)),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)),
//																	enableContent1,
//																	member1, member2);
//		
//		//when
//		ReservationEntity first = reservationService.registReservation(firstReservation);
//		first.cancel();
//		
//		
//		//then
//		ReservationEntity second = reservationService.registReservation(secondReservation);
//		
//		assertThat(first.getState()).isEqualTo(ReservationState.CANCEL);
//		assertThat(second.getState()).isEqualTo(ReservationState.OK);
//	}
//	
//	
//	
//	@Test
//	void _3_5?????????????????????_????????????????????????_?????????_??????() {
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist secondReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist cancelReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
//																	enableContent1,
//																	member1);
//		
//		ReservationDTO.Regist fourthReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
//																	enableContent1,
//																	member1);
//		
//		
//		ReservationDTO.Regist fiveReservation = createReservation(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30)),
//																	LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 40)),
//																	enableContent1,
//																	member1);
//		
//		//when
//		ReservationEntity first = reservationService.registReservation(firstReservation);
//		ReservationEntity second = reservationService.registReservation(secondReservation);
//		ReservationEntity third = reservationService.registReservation(cancelReservation);
//		third.cancel();
//		
//		ReservationEntity fourth = reservationService.registReservation(fourthReservation);
//		
//		
//		
//		//then
//		OverTimeUseMemberException ex = assertThrows(OverTimeUseMemberException.class, () -> {
//			reservationService.registReservation(fiveReservation);
//		});
//
//		assertThat(ex.getOverTimeMembers()).isNotNull().isNotEmpty();
//		assertThat(ex.getOverTimeMembers().get(0).getUsedMinute()).isEqualTo(90);
//	}
//	
//	
//	
//	//4.????????????
//	//4.1
//	@Test
//	void _4_1????????????_??????() {
//		
//		//given
//		ReservationDTO.Regist firstReservation = createReservation(LocalDateTime.of(2021, 9, 7, 10, 0),
//																   LocalDateTime.of(2021, 9, 7, 10, 30),
//																   enableContent1,
//																   member1);
//		ReservationEntity first = reservationService.registReservation(firstReservation);
//		
//		//when
//		ReservationDTO.Details updateReservation = new ReservationDTO.Details();
//		updateReservation.setReservationId(first.getReservationId());
//		updateReservation.setStartTime(LocalDateTime.of(2021, 9, 7, 11, 0));
//		updateReservation.setEndTime(LocalDateTime.of(2021, 9, 7, 11, 30));
//		
//		List<MemberDTO.MemberDetails> memberList = firstReservation.getMembers();
//		memberList.clear();
//		
//		MemberDTO.MemberDetails newMember = MemberDTO.MemberDetails.of(member2);
//		memberList.add(newMember);
//		
//		updateReservation.setMembers(memberList);
//		updateReservation.setContents(firstReservation.getContents());
//		
//		reservationService.updateReservation(updateReservation);
//		
//		//then
//		ReservationEntity reservation = reservationService.getReservationDetails(first.getReservationId());
//		
//		assertThat(reservation.getStartTime()).isEqualTo(updateReservation.getStartTime());
//		assertThat(reservation.getEndTime()).isEqualTo(updateReservation.getEndTime());
//		assertThat(reservation.getMembers()).isNotEmpty();
//		assertThat(reservation.getMembers().contains(member2));
//		assertThat(reservation.getContents()).isNotNull();
//		assertThat(reservation.getContents()).isEqualTo(first.getContents());
//		
//	}
//	
//	
//	
//	
//	
//	public static ReservationDTO.Regist createReservation(LocalDateTime startTime, LocalDateTime endTime, ContentsEntity contents, MemberEntity...members){
//		
//		
//		ReservationDTO.Regist reservation = new ReservationDTO.Regist();
//		
//		List<MemberDTO.MemberDetails> memberList = Arrays.asList(members)
//														 .stream()
//														 .map( m -> MemberDTO.MemberDetails.of(m)).collect(Collectors.toList());
//		
//		reservation.setStartTime(startTime);
//		reservation.setEndTime(endTime);
//		reservation.setContents(ContentsDTO.Details.of(contents));
//		reservation.setMembers(memberList);
//		
//		return reservation;
//	}
//
//}
