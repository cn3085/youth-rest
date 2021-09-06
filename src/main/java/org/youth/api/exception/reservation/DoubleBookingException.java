package org.youth.api.exception.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoubleBookingException extends RuntimeException {

	private static final long serialVersionUID = -6205335711594624593L;
	
	@Override
	public String getMessage() {
		return "해당 시간에 이미 등록된 예약이 있습니다.";
	}
}
