package org.youth.api.entity;

import java.time.LocalTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;

import org.youth.api.dto.SettingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "setting")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SettingEntity extends BaseDataEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long settingId;
	
	private LocalTime sunDayStart;
	private LocalTime sunDayEnd;
	
	private LocalTime monDayStart;
	private LocalTime monDayEnd;
	
	private LocalTime tuesDayStart;
	private LocalTime tuesDayEnd;
	
	private LocalTime wednesDayStart;
	private LocalTime wednesDayEnd;
	
	private LocalTime thursDayStart;
	private LocalTime thursDayEnd;
	
	private LocalTime friDayStart;
	private LocalTime friDayEnd;
	
	private LocalTime saturDayStart;
	private LocalTime saturDayEnd;
	
	private Integer reservationMaxMinute;

	
	
	public void updateValues(@Valid SettingDTO settingDTO) {
		
		 this.sunDayStart = settingDTO.getSunDayStart();
		 this.sunDayEnd = settingDTO.getSunDayEnd();
		 this.monDayStart = settingDTO.getMonDayStart();
		 this.monDayEnd = settingDTO.getMonDayEnd();
		 this.tuesDayStart = settingDTO.getTuesDayStart();
		 this.tuesDayEnd = settingDTO.getTuesDayEnd();
		 this.wednesDayStart = settingDTO.getWednesDayStart();
		 this.wednesDayEnd = settingDTO.getWednesDayEnd();
		 this.thursDayStart = settingDTO.getThursDayStart();
		 this.thursDayEnd = settingDTO.getThursDayEnd();
		 this.friDayStart = settingDTO.getFriDayStart();
		 this.friDayEnd = settingDTO.getFriDayEnd();
		 this.saturDayStart = settingDTO.getSaturDayStart();
		 this.saturDayEnd = settingDTO.getSaturDayEnd();
		 this.reservationMaxMinute = settingDTO.getReservationMaxMinute();
	}
}
