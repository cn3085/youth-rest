package org.youth.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.MemberDTO;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;
import org.youth.api.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final ReservationRepository reservationRepository;

	
	@Transactional(readOnly = true)
	public Page<ReservationDTO.Details> getReservation(Pageable page, ReservationParam searchParam) {
		
		return reservationRepository.searchAll(page, searchParam).map(ReservationDTO.Details::of);
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
	public void updateReservation(@Valid ReservationDTO.Details reservationDTO) {
		
		checkPossibleChangeReservationTime(reservationDTO);
		
		ReservationEntity reservation = reservationDTO.toEntity();
		reservationRepository.save(reservation);
	}
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteReservation(long reservationId) {
		
		ReservationEntity reservation = getReservationDetails(reservationId);
		reservationRepository.delete(reservation);
	}
	
	
	
	public void checkPossibleToReservation(ReservationDTO.Regist reservationDTO) {
		
		List<ReservationEntity> overwrapReservations = getReservationByTime(reservationDTO.getStartTime(), reservationDTO.getEndTime());
		
		checkDoubleBooking(overwrapReservations, reservationDTO.getContents().getContentsId());
		checkMemberUsingAnotherContetns(overwrapReservations, reservationDTO.getMembers());
		
	}
	
	
	
	public void checkPossibleChangeReservationTime(ReservationDTO.Details reservationDTO) {
		
		List<ReservationEntity> overwrapReservations = getReservationByTimeExcludeThis(reservationDTO.getStartTime(), reservationDTO.getEndTime(), reservationDTO.getReservationId());
		
		checkDoubleBooking(overwrapReservations, reservationDTO.getContents().getContentsId());
		checkMemberUsingAnotherContetns(overwrapReservations, reservationDTO.getMembers());
		
	}
	
	

	private List<ReservationEntity> getReservationByTimeExcludeThis(LocalDateTime startTime, LocalDateTime endTime, long reservationId) {
		return reservationRepository.findByReservationTimeExcludeThis(startTime, endTime, reservationId);
	}



	private List<ReservationEntity> getReservationByTime(LocalDateTime startTime, LocalDateTime endTime) {
		return reservationRepository.findByReservationTime(startTime, endTime);
	}



	private void checkDoubleBooking(List<ReservationEntity> overwrapReservations, long contentsId) {
		boolean isDoubleBooking = overwrapReservations.stream().anyMatch( r -> r.getContents().getContentsId().equals(contentsId));
		
		if(isDoubleBooking) {
			throw new DoubleBookingException();
		}
	}
	
	
	
	private void checkMemberUsingAnotherContetns(List<ReservationEntity> overwrapReservations,
												 List<MemberDTO.Details> memberList) {
		
		List<Long> reservationMemberIds = memberList.stream().map(MemberDTO.Details::getMemberId).collect(Collectors.toList());
		
		List<MemberDTO.DoubleBookingRes> bannedMemberList = new ArrayList<>();
		
		for(ReservationEntity reservation : overwrapReservations) {
			
			List<MemberDTO.DoubleBookingRes> anotherUsingMemberList =
					reservation.getMembers().stream().filter( m ->
					reservationMemberIds.contains(m.getMemberId())).map( m -> {
						
						MemberDTO.DoubleBookingRes bannedMember = new MemberDTO.DoubleBookingRes();
						bannedMember.setMemberId(m.getMemberId());
						bannedMember.getReservations().add(ReservationDTO.DoubleBookingRes.of(reservation));
						
						return bannedMember;
					}).collect(Collectors.toList());
			
			bannedMemberList.addAll(anotherUsingMemberList);
		}
		
		if(!bannedMemberList.isEmpty()) {
			throw new ContainsAnotherReservationException(bannedMemberList);
		}
		
	}

}
