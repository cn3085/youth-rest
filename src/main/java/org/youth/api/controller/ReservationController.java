package org.youth.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.dto.ReservationParam;
import org.youth.api.dto.ResponseDTO;
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
	
	
	
	@PostMapping
	public ResponseEntity<ResponseDTO> registReservation(@RequestBody @Valid ReservationDTO.Regist reservationDTO){
		
		reservationService.registReservation(reservationDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
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
	
	
	
	@PutMapping("/{reservationId}")
	public ResponseEntity<ResponseDTO> updateReservation(@PathVariable Long reservationId,
													@RequestBody @Valid ReservationDTO.Details reservationDTO){
		
		reservationService.updateReservation(reservationId, reservationDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.build());
	}
}