package org.youth.api.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.code.ReservationState;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ContentsEntity;
import org.youth.api.entity.MemberEntity;
import org.youth.api.entity.ReservationEntity;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;
import org.youth.api.exception.reservation.OverTimeUseMemberException;
import org.youth.api.exception.reservation.ReservationRestrictContentsException;
import org.youth.api.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final ReservationRepository reservationRepository;
	private final ContentsService contentsService;
	private final MemberService memberService;
	private final SettingService settingService;

	
	@Transactional(readOnly = true)
	public Page<ReservationDTO.Details> getReservation(Pageable page, ReservationParam searchParam) {
		
		return reservationRepository.searchAll(page, searchParam).map(ReservationDTO.Details::of);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<ReservationDTO.Details> getReservationList(ReservationParam searchParam) {
		return reservationRepository.searchAll(searchParam).stream().map(ReservationDTO.Details::of).collect(Collectors.toList());
	}
	
	
	
	private List<ReservationEntity> getReservations(ReservationParam param){
		return reservationRepository.searchAll(param);
	}
	
	
	
	@Transactional(readOnly = true)
	public ReservationEntity getReservationDetails(long reservationId) {
		return reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다. " + reservationId));
	}

	
	
	@Transactional(rollbackFor = Exception.class)
	public ReservationEntity registReservation(@Valid ReservationDTO.Regist reservationDTO) {
		
		checkPossibleToReservation(reservationDTO);
		
		ReservationEntity reservation = reservationDTO.toEntity();
		return reservationRepository.save(reservation);
	}


	
	@Transactional(rollbackFor = Exception.class)
	public ReservationEntity updateReservation(@Valid ReservationDTO.Details reservationDTO) {
		
		checkPossibleChangeReservationTime(reservationDTO);
		
		ReservationEntity reservation = getReservationDetails(reservationDTO.getReservationId());
		
		reservation.updateDetails(reservationDTO);
//		ReservationEntity reservation = reservationDTO.toEntity();
		return reservationRepository.save(reservation);
	}
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteReservation(long reservationId) {
		
		ReservationEntity reservation = getReservationDetails(reservationId);
		reservation.delete();
		reservationRepository.delete(reservation);
	}
	
	
	
	private void checkPossibleToReservation(ReservationDTO.Regist reservationDTO) {
		final Long NOT_YET_RESERVATION_ID = -1L;
		checkEnableReservationContents(reservationDTO.getContents());
		
		List<ReservationEntity> overwrapReservations = getReservationByTime(reservationDTO.getStartTime(), reservationDTO.getEndTime());
		
		checkDoubleBooking(overwrapReservations, reservationDTO.getContents().getContentsId());
		checkMemberOvertimeUseThisContents(NOT_YET_RESERVATION_ID, reservationDTO.getStartTime(), reservationDTO.getEndTime(), reservationDTO.getContents(), reservationDTO.getMembers());
		checkMemberUsingAnotherContetns(overwrapReservations, reservationDTO.getMembers());
		
	}

	

	private void checkPossibleChangeReservationTime(ReservationDTO.Details reservationDTO) {
		
		checkEnableReservationContents(reservationDTO.getContents());
		
		List<ReservationEntity> overwrapReservations = getReservationByTimeExcludeThis(reservationDTO.getStartTime(), reservationDTO.getEndTime(), reservationDTO.getReservationId());
		
		checkDoubleBooking(overwrapReservations, reservationDTO.getContents().getContentsId());
		checkMemberOvertimeUseThisContents(reservationDTO.getReservationId(), reservationDTO.getStartTime(), reservationDTO.getEndTime(), reservationDTO.getContents(), reservationDTO.getMembers());
		checkMemberUsingAnotherContetns(overwrapReservations, reservationDTO.getMembers());
		
	}
	
	

	private List<ReservationEntity> getReservationByTimeExcludeThis(LocalDateTime startTime, LocalDateTime endTime, long reservationId) {
		return reservationRepository.findByReservationTimeExcludeThis(startTime, endTime, reservationId);
	}



	private List<ReservationEntity> getReservationByTime(LocalDateTime startTime, LocalDateTime endTime) {
		return reservationRepository.findByReservationTime(startTime, endTime);
	}
	
	
	
	private void checkEnableReservationContents(ContentsDTO.Details contentsDTO) {
		ContentsEntity contents = contentsService.getContentsDetails(contentsDTO.getContentsId());
		if(!contents.isEnableReservation()) {
			throw new ReservationRestrictContentsException(contents.getNotice());
		}
	}



	private void checkDoubleBooking(List<ReservationEntity> overwrapReservations, long contentsId) {
		boolean isDoubleBooking = overwrapReservations.stream().anyMatch( r -> r.getContents().getContentsId().equals(contentsId));
		
		if(isDoubleBooking) {
			throw new DoubleBookingException();
		}
	}
	
	
	
	private void checkMemberUsingAnotherContetns(List<ReservationEntity> overwrapReservations,
												 List<MemberDTO.MemberDetails> memberList) {
		
		List<Long> reservationMemberIds = memberList.stream().map(MemberDTO.MemberDetails::getMemberId).collect(Collectors.toList());
		
		List<MemberDTO.DoubleBookingRes> bannedMemberList = new ArrayList<>();
		
		for(ReservationEntity reservation : overwrapReservations) {
			
			List<MemberDTO.DoubleBookingRes> anotherUsingMemberList =
					reservation.getMembers().stream().filter( m ->
					reservationMemberIds.contains(m.getMemberId())).map( m -> {
						
						MemberDTO.DoubleBookingRes bannedMember = new MemberDTO.DoubleBookingRes();
						bannedMember.setMemberId(m.getMemberId());
						bannedMember.setName(m.getName());
						bannedMember.getReservations().add(ReservationDTO.DoubleBookingRes.of(reservation));
						
						return bannedMember;
					}).collect(Collectors.toList());
			
			bannedMemberList.addAll(anotherUsingMemberList);
		}
		
		if(!bannedMemberList.isEmpty()) {
			throw new ContainsAnotherReservationException(bannedMemberList);
		}
		
	}
	
	
	
	private void checkMemberOvertimeUseThisContents(Long reservationId, LocalDateTime startTime, LocalDateTime endTime, ContentsDTO.Details contents, List<MemberDTO.MemberDetails> members) {

		final long reservationMaxMinute = settingService.getSettingValues().getReservationMaxMinute();
		final long reservationMinute = Duration.between(startTime.toLocalTime(), endTime.toLocalTime()).toMinutes();
		ReservationParam param = new ReservationParam();
		
		List<MemberDTO.OverTimeUseRes> overTimeUseMembers = new ArrayList<>();
		
		for(MemberDTO.MemberDetails member : members) {
			
			param.setCName(contents.getName());
			param.setMId(member.getMemberId());
			param.setSt(ReservationState.OK);
			param.setSdt(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
			param.setEdt(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
			
			List<ReservationEntity> todayReservationForMember = getReservations(param);
			
			long useMinute = todayReservationForMember.stream().map( r -> {
							if(r.getReservationId() == reservationId) { //현재 등록된 예약시간은 제외하구 계산함
								return 0L;
							}else {
								Duration diff = Duration.between(r.getStartTime().toLocalTime(), r.getEndTime().toLocalTime());
								return diff.toMinutes();
							}
			}).reduce(0L, (x, y) -> x + y);
			
			long sumMinute = useMinute + reservationMinute;
			
			if(reservationMaxMinute < sumMinute ) {
				
				MemberEntity overTimeMmeber = memberService.getMemberDetails(member.getMemberId());
				
				MemberDTO.OverTimeUseRes overTimeDTO = MemberDTO.OverTimeUseRes.of(overTimeMmeber);
				
				overTimeDTO.setUsedMinute(useMinute);
				overTimeDTO.setReservationMinute(reservationMinute);
				
				overTimeUseMembers.add(overTimeDTO);
			}
			
		}
		
		if(!overTimeUseMembers.isEmpty()) {
			throw new OverTimeUseMemberException(reservationMaxMinute, overTimeUseMembers);
		}
	}



	@Transactional(rollbackFor = Exception.class)
	public void cancelReservation(long reservationId) {
		
		ReservationEntity reservation = getReservationDetails(reservationId);
		reservation.cancel();
		
	}

	
}
