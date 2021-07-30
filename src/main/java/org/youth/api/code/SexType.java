package org.youth.api.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SexType {
	M("남자"),
	W("여자");
	
	private final String value;
	public String value() { return value; }
}
