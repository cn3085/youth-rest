package org.youth.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ReservationState;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.exception.reservation.ContainsAnotherReservationException;
import org.youth.api.exception.reservation.DoubleBookingException;
import org.youth.api.exception.reservation.OverTimeUseMemberException;
import org.youth.api.exception.reservation.ReservationRestrictContentsException;
import org.youth.api.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	@GetMapping
	public ResponseEntity<ResponseDTO> getReservationList(@PageableDefault(page = 0, size = 20) Pageable page,
													   	  ReservationParam searchParam){
		
		Page<ReservationDTO.Details> pageContent = reservationService.getReservation(page, searchParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(pageContent)
											.build());
	}
	
	
	
	@GetMapping("/contents-timeline")
	public ResponseEntity<ResponseDTO> getReservationList(ReservationParam searchParam){
		
		searchParam.setSt(ReservationState.OK);
		
		List<ReservationDTO.Details> reservationList = reservationService.getReservationList(searchParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(reservationList)
											.build());
	}
	
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> registReservation(@RequestBody @Valid ReservationDTO.Regist reservationDTO){
		
		ReservationDTO.Details reservation =  ReservationDTO.Details.of(reservationService.registReservation(reservationDTO));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(reservation)
											.message("예약이 등록되었습니다.")
											.build());
	}
	
	
	
	@GetMapping("/{reservationId}")
	public ResponseEntity<ResponseDTO> getThisReservation(@PathVariable Long reservationId){
		
		ReservationDTO.Details memberDTO = ReservationDTO.Details.of(reservationService.getReservationDetails(reservationId));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.data(memberDTO)
											.code(ResponseCode.SUCC)
											.build());
	}
	
	
	
	@DeleteMapping("/{reservationId}")
	public ResponseEntity<ResponseDTO> deleteReservation(@PathVariable Long reservationId){
		
		reservationService.deleteReservation(reservationId);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.build());
	}
	
	
	
	@PutMapping
	public ResponseEntity<ResponseDTO> updateReservation(@RequestBody @Valid ReservationDTO.Details reservationDTO){
		
		ReservationDTO.Details reservation = ReservationDTO.Details.of(reservationService.updateReservation(reservationDTO));
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.data(reservation)
											.message("수정이 완료되었습니다.")
											.code(ResponseCode.SUCC)
											.build());
	}
	
	
	@PutMapping("/{reservationId}/cancel")
	public ResponseEntity<ResponseDTO> cancelReservation(@PathVariable Long reservationId){
		
		reservationService.cancelReservation(reservationId);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.message("예약이 취소되었습니다.")
											.build());
	}
	
	
	
	@ExceptionHandler(value = ReservationRestrictContentsException.class)
	public ResponseEntity<ResponseDTO> handleException(ReservationRestrictContentsException e){
		return new ResponseEntity<>(ResponseDTO.builder()
											   .code(ResponseCode.R_RESTRICT_CONTENTS)
											   .message(e.getMessage())
										   	   .build(), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(value = DoubleBookingException.class)
	public ResponseEntity<ResponseDTO> handleException(DoubleBookingException e){
		return new ResponseEntity<>(ResponseDTO.builder()
												.code(ResponseCode.R_DOUBLE_BOOKING)
												.message(e.getMessage())
												.build(), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(value = ContainsAnotherReservationException.class)
	public ResponseEntity<ResponseDTO> handleException(ContainsAnotherReservationException e){
		return new ResponseEntity<>(ResponseDTO.builder()
												.code(ResponseCode.R_CONTAINS_ANOTHER_RESERVATION)
												.data(e.getBannedMemberList())
												.message(e.getMessage())
												.build(), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(value = OverTimeUseMemberException.class)
	public ResponseEntity<ResponseDTO> handleException(OverTimeUseMemberException e){
		return new ResponseEntity<>(ResponseDTO.builder()
												.code(ResponseCode.R_OVERTIME_USE_MEMBER)
												.data(e.getOverTimeMembers())
												.message(e.getMessage())
												.build(), HttpStatus.OK);
	}
	
	
}
