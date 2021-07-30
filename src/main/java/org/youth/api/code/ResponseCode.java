package org.youth.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
	
	SUCC("성공"),
	FAIL("실패");
	
	private String value;
}
