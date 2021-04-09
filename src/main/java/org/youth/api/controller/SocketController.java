package org.youth.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.youth.api.dto.BookingDTO;
import org.youth.api.service.BookingService;

@RestController
public class SocketController {
	
	private final BookingService bookingService;
	
	public SocketController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@MessageMapping("/hello")
	@SendTo("/topic/roomId")
	public BookingDTO broadCast(BookingDTO bookinDTO) {
		bookingService.booking(bookinDTO);
		return bookinDTO;
	}
	
}
