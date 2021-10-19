package org.youth.api.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.youth.api.code.ReservationState;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationParam {
	
	private String cName; //contentsName
	private Long cId;
	private String mName; //memberName
	private Long mId;
	
	private ReservationState st;
	
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //2021-10-30T15:00:00
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime sdt; //reservationStarttime
	
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime edt; //reservationStarttime
	
}
