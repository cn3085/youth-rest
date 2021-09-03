package org.youth.api.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.entity.ReservationEntity;
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
	public void registReservation(@Valid ReservationDTO.Regist reservationDTO) {
		
		ReservationEntity reservation = reservationDTO.toEntity();
		reservationRepository.save(reservation);
		
		//1. 이 컨텐츠가 현재 시간에 예약되어 있는지
		//2. 이 컨텐츠를 이용하는 사람이 현재 다른 컨텐츠를 이용 중인지
	}


	
	@Transactional(rollbackFor = Exception.class)
	public void updateReservation(long reservationId, @Valid ReservationDTO.Details reservationDTO) {
		
		ReservationEntity reservation = reservationDTO.toEntity();
		reservationRepository.save(reservation);
	}
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteReservation(long reservationId) {
		
		ReservationEntity reservation = getReservationDetails(reservationId);
		reservationRepository.delete(reservation);
	}
	
	
	
	public void checkPossibleToReservation() {
		
		checkAlreadyReservation();
		checkAnotherContentsReservationMember();
	}



	private void checkAlreadyReservation() {
		reservationRepository.findByContents
		
	}


}
