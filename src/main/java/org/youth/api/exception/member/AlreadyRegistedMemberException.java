package org.youth.api.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlreadyRegistedMemberException extends RuntimeException {

	private static final long serialVersionUID = -8011759922079799582L;

	private String fieldName;
	private String value;
	
	@Override
	public String getMessage() {
		return "이미 등록된 정보입니다.";
	}
	
	
}
