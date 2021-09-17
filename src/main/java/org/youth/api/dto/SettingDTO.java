package org.youth.api.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.youth.api.entity.SettingEntity;
import org.youth.api.mapper.SettingMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SettingDTO {
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime sunDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime sunDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime monDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime monDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime tuesDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime tuesDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime wednesDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime wednesDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime thursDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime thursDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime friDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime friDayEnd;
	
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime saturDayStart;
	@NotNull @DateTimeFormat(pattern = "HH:mm")private LocalTime saturDayEnd;
	
	@NotNull private Integer reservationMaxMinute;
	
	
	public static SettingDTO of(SettingEntity setting) {
		return SettingMapper.INSTANCE.of(setting);
	}
}
