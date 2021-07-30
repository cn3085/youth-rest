package org.youth.api.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
	
	/*
	 * private final ManagementWebSocketHandler managermentHandler; private final
	 * DietWebSocketHandler foodHandler;
	 */
	

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		/*
		 * registry.addHandler(managermentHandler, "/websocket/management")
		 * .addHandler(foodHandler, "/websocket/diet") .setAllowedOrigins("*");
		 */
	}
	
}
