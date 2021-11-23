package org.youth.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
	
	SUCC("성공"),
	FAIL("실패"),
	
	R_RESTRICT_CONTENTS("제한된 컨텐츠"),
	R_DOUBLE_BOOKING("중복 예약"),
	R_CONTAINS_ANOTHER_RESERVATION("다른 예약을 진행 중인 회원 포함"),
	R_OVERTIME_USE_MEMBER("해당 컨텐츠 이용시간 초과");
	
	private String value;
}
