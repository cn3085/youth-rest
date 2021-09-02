package org.youth.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youth.api.dto.ContentsDTO;
import org.youth.api.entity.ContentsEntity;

@Mapper
public interface ContentsMapper {

	ContentsMapper INSTANCE = Mappers.getMapper(ContentsMapper.class);
	
	@Mapping(target = "contentsId", ignore = true)
	ContentsEntity toEntity(ContentsDTO.Regist regist);
	
	ContentsEntity toEntity(ContentsDTO.Details regist);

	ContentsDTO.Details of(ContentsEntity contentsDetails);
	
}
