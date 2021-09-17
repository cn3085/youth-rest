package org.youth.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ResponseDTO;
import org.youth.api.dto.SettingDTO;
import org.youth.api.service.SettingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/settings")
public class SettingController {
	
	private final SettingService settingService;
	
	@GetMapping
	public ResponseEntity<ResponseDTO> getContentsList(){
	
		SettingDTO setting = SettingDTO.of(settingService.getSettingValues());
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.data(setting)
											.build());
	}
	
	
	
	@PutMapping
	public ResponseEntity<ResponseDTO> updateContents(@RequestBody @Valid SettingDTO settingDTO){
			
		settingService.updateSetting(settingDTO);
		
		return ResponseEntity.ok(ResponseDTO.builder()
											.code(ResponseCode.SUCC)
											.build());
	}

}
