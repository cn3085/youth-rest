package org.youth.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youth.api.dto.StatisticsParam;
import org.youth.api.mybatis.StatisticsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
	
	
	private final StatisticsMapper statisticsMapper;

	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findMostUsedMember(StatisticsParam searchParam) {
		
		return statisticsMapper.findMostUsedMember(searchParam);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findReservationAverageOfEachContents(StatisticsParam searchParam) {
		
		return statisticsMapper.findReservationAverageOfEachContents(searchParam);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findTotalUseTimeEachContents(StatisticsParam searchParam) {
		
		return statisticsMapper.findTotalUseTimeEachContents(searchParam);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findUseCountEachContentsByMemberBirth(StatisticsParam searchParam) {
		
		return statisticsMapper.findUseCountEachContentsByMemberBirth(searchParam);
	}
	
}
