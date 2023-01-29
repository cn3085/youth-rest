package org.youth.api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.youth.api.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiScheduler {
	
	private final ReservationService reservationService;

	@Scheduled(cron = "0 0 10 1/1 * *")
	public void doJob() {
		reservationService.deleteExpireDate();
	}
	
}