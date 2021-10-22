package org.youth.api.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsParam {
	
	private LocalDate sd; //시작일 
	private LocalDate ed; //종료일
	
	private long cid; //콘텐츠 아이디
	
}
