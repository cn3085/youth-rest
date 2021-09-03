package org.youth.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youth.api.dto.ReservationDTO;
import org.youth.api.entity.ReservationEntity;

@Mapper
public interface ReservationMapper {

	ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);
	
	@Mapping(target = "reservationId", ignore = true)
	ReservationEntity toEntity(ReservationDTO.Regist regist);
	
	ReservationEntity toEntity(ReservationDTO.Details regist);

	ReservationDTO.Details of(ReservationEntity reservationDetails);
	
}
