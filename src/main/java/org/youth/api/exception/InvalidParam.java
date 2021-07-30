package org.youth.api.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidParam {
	
	private String field;
	private Object value;
	private String message;
}
