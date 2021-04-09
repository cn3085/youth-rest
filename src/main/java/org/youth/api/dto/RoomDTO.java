package org.youth.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
	
	private long roomId;
	private String roomName;
	
	@Builder
	public RoomDTO(long roomId, String roomName) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
	}

	
}
