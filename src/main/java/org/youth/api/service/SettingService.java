package org.youth.api.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.SettingDTO;
import org.youth.api.entity.SettingEntity;
import org.youth.api.repository.SettingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingService {

	private final SettingRepository settingRepository;

	
	@Transactional(readOnly = true)
	public SettingEntity getSettingValues() {
		
		return settingRepository.findFirstByOrderBySettingIdDesc().orElseThrow( () -> new IllegalStateException("등록된 설정값이 존재하지 않습니다. 시스템을 점검하십시오."));
	}

	
	
	@Transactional(rollbackFor = Exception.class)
	public void updateSetting(@Valid SettingDTO settingDTO) {
		
		SettingEntity setting = getSettingValues();
//		
		setting.updateValues(settingDTO);
//		SettingEntity setting = SettingEntity.builder()
//											 .sunDayStart(settingDTO.getSunDayStart())
//											 .sunDayEnd(settingDTO.getSunDayEnd())
//											 .monDayStart(settingDTO.getMonDayStart())
//											 .monDayEnd(settingDTO.getMonDayEnd())
//											 .tuesDayStart(settingDTO.getTuesDayStart())
//											 .tuesDayEnd(settingDTO.getTuesDayEnd())
//											 .wednesDayStart(settingDTO.getWednesDayStart())
//											 .wednesDayEnd(settingDTO.getWednesDayEnd())
//											 .thursDayStart(settingDTO.getThursDayStart())
//											 .thursDayEnd(settingDTO.getThursDayEnd())
//											 .friDayStart(settingDTO.getFriDayStart())
//											 .friDayEnd(settingDTO.getFriDayEnd())
//											 .saturDayStart(settingDTO.getSaturDayStart())
//											 .saturDayEnd(settingDTO.getSaturDayEnd())
//											 .reservationMaxMinute(settingDTO.getReservationMaxMinute()).build();
		settingRepository.save(setting);
	} 
	
	
}
