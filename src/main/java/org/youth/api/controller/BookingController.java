package org.youth.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.dto.RoomDTO;
import org.youth.api.service.BookingService;
import org.youth.api.service.RoomService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {
	
	private final BookingService bookingService;
	private final RoomService roomService;
	
	@Autowired
	public BookingController(BookingService bookingService, RoomService roomService) {
		this.bookingService = bookingService;
		this.roomService = roomService;
	}
	
	@GetMapping("/booking")
	public ResponseEntity<String> test(@RequestParam("data")Integer data) {
		
		try {
			bookingService.booking(data);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("예약 완료", HttpStatus.OK); 
	}
	
	@GetMapping("/room")
	public ResponseEntity<List<RoomDTO>> showRoomList(){
		List<RoomDTO> roomList = roomService.getRoomList();
		return new ResponseEntity<>(roomList, HttpStatus.OK);
	}
	
	
}
