package org.youth.api.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsParam {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate sd; //시작일 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ed; //종료일
	
	private Long cId; //콘텐츠 아이디
	
}
