package org.youth.api.config.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	//스케줄러를 빈으로 등록하기만 하면 알아서 위에서 생성한 스케줄러에 작업을 할당해준다.	
	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(4);
		return scheduler;
	}
//	@Bean
//	public TaskScheduler poolScheduler() {
//		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler(); 
//		threadPoolTaskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2); 
//		threadPoolTaskScheduler.setThreadNamePrefix("jeong-pro-threadpool"); 
//		
//		return threadPoolTaskScheduler;
//	}
}
