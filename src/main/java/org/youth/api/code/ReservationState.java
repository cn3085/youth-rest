package org.youth.api.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReservationState {
	
	OK("예약완료"), CANCEL("예약취소");
	
	private final String value;
}
