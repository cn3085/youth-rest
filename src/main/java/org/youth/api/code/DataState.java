package org.youth.api.code;

import lombok.Getter;

@Getter
public enum DataState {
	
	A("사용"), D("삭제");
	
	private String description;
	
	private DataState (String description) {
		this.description = description;
	}
}
