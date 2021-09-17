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

	private String nm;
	
	private SexType st;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate ab;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate bb;
	
	private String mp;
	
	private String pp;
	
	private String adrs;
	
	private String sch;
	
	private String gd;
	
	private String mm;
	
}
