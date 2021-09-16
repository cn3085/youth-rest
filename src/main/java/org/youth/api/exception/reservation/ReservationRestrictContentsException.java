package org.youth.api.exception.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationRestrictContentsException extends RuntimeException {

	private static final long serialVersionUID = -7154349387184857124L;
	
	private final String notice;

	@Override
	public String getMessage() {
		return "예약이 제한된 컨텐츠입니다. (" + notice + ")";
	}
}
