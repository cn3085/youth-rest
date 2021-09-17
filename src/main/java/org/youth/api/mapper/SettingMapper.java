package org.youth.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.youth.api.dto.SettingDTO;
import org.youth.api.entity.SettingEntity;

@Mapper
public interface SettingMapper {

	SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);
	
	SettingDTO of(SettingEntity setting);
}
