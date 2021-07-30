package org.youth.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.youth.api.dto.MemberDTO;
import org.youth.api.entity.MemberEntity;

@Mapper
public interface MemberMapper {
	MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

	@Mapping(target = "memberId", ignore = true)
	MemberEntity toEntity(MemberDTO.Regist regist);
}
