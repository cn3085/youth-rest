package org.youth.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youth.api.dto.BookingDTO;
import org.youth.api.entity.BookingEntity;
import org.youth.api.repository.BookingRepository;

@Service
public class BookingService {
	
	private final BookingRepository bookingRepository;
	
	@Autowired
	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	private static List<Integer> DB = new ArrayList<>();

	public synchronized void booking(int data) throws Exception {
		
		if(isAlreadyBooked(data)) {
			throw new Exception("이미 예약되었음");
		}
		
		DB.add(data);
		
		System.out.println(DB);
	}
	
	private boolean isAlreadyBooked(int data) {
		
		boolean isAlreadyBooked = false;
		isAlreadyBooked = DB.contains(data);
		
		try {
			Thread.sleep(3000);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return isAlreadyBooked; 
	}
	
	public void booking(BookingDTO bookinDTO) {
		
		BookingEntity bookingEntity = BookingEntity.builder()
												   .roomId(bookinDTO.getRoomId())
												   .roomName(bookinDTO.getRoomName())
												   .userName(bookinDTO.getUserName())
												   .build();
		
		bookingRepository.save(bookingEntity);
	}
	
	

}
