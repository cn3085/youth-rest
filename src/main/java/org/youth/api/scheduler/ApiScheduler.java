package org.youth.api.scheduler;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiScheduler {

	

//	@Scheduled(fixedDelay = 600000, initialDelay = 600000)
//	public void doJob() {
//		
//		String today = CommUtil.getToday("");
//		String time = CommUtil.getTime("");
//		
//		weatherService.updateWeatherData(today, time);
//	}
	
	
//	@Scheduled(cron = "0 0 23 1/1 * *")
//	public void pullLectureFromCMS() {
//		try {
//			lectureService.pullLuctureDataFromLMS();
//		}catch(Exception e) {
//			log.error(e.toString(), e.getMessage());
//		}
//	}
	
}