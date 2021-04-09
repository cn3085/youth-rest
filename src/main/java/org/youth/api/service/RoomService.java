package org.youth.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.youth.api.dto.RoomDTO;

@Service
public class RoomService {

	public List<RoomDTO> getRoomList() {
		
		RoomDTO room1 = RoomDTO.builder().roomId(1L).roomName("방1").build();
		RoomDTO room2 = RoomDTO.builder().roomId(2L).roomName("방2").build();
		RoomDTO room3 = RoomDTO.builder().roomId(3L).roomName("방3").build();
		RoomDTO room4 = RoomDTO.builder().roomId(4L).roomName("방4").build();
		RoomDTO room5 = RoomDTO.builder().roomId(5L).roomName("방5").build();
		
		List<RoomDTO> rooms = new ArrayList<>();
		
		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		rooms.add(room4);
		rooms.add(room5);
		
		return rooms;
	}

}
