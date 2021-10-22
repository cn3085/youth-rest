package org.youth.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.dto.StatisticsParam;
import org.youth.api.service.StatisticsService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/statistics")
public class StatisticsController {

	private final StatisticsService reservationService;
	
	@GetMapping("/most-used-member")
	public ResponseEntity<ResponseDTO> getMostUsedMember(StatisticsParam searchParam){
		
		List<Map<String, Object>> memberList =  reservationService.getMostUsedMember(searchParam);
		
		return ResponseEntity.ok(ResponseDTO.builder()
							 				.code(ResponseCode.SUCC)
							 				.data(memberList)
							 				.build());
	}
}
