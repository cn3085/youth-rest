package org.youth.api.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.youth.api.dto.StatisticsParam;

@Mapper
public interface StatisticsMapper {
	
	public List<Map<String, Object>> findMostUsedMember(@Param("s") StatisticsParam searchParam);
}
