package org.youth.api.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.youth.api.code.SexType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberParam {

	private String nm; //이름
	
	private SexType st; //성별
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate ab; //생일 시작 검색
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate bb; //생일 종료 검색
	
	private String mp; //폰번호
	
	private String pp; //부모님 폰번호
	
	private String adrs; //주소
	
	private String sch; //학교
	
	private String gd; //학년
	
	private String mm; //메모
	
}
