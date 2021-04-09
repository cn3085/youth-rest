package org.youth.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {
	
	private long roomId;
	private String roomName;
	private String userName;
	
	@Builder
	public BookingDTO(long roomId, String roomName, String userName) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.userName = userName;
	}

}
