package org.youth.api.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.youth.api.code.DataState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDTO {
	
	private Long id;
	
	private String userId;

	private String password;
	
	private List<String> roles = new ArrayList<>();

	
	@Getter
	@Setter
	@ToString
	public static class UserRequest{
		private String userId;
		private String password;
	}
	
	
}
