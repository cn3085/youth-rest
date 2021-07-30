package org.youth.api.dto;


import org.youth.api.code.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

	private ResponseCode code; //에러 코드
	
	private String message; //메세지
	
	private String redirection; //redirection할 url
	
	private Object data; //데이터
}
