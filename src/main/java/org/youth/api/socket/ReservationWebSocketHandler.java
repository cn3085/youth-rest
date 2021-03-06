package org.youth.api.socket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReservationWebSocketHandler extends TextWebSocketHandler {

	private static Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		
		sessions.add(session);
		
		log.info("client{} connect.", session.getRemoteAddress());
	}
	
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("client{} handle message: {}", session.getRemoteAddress(), message.getPayload());
		for(WebSocketSession webSocketSession : sessions) {
			if(session == webSocketSession ) continue;
			webSocketSession.sendMessage(message);
		}
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		sessions.remove(session);
		log.info("client{} disconnect.", session.getRemoteAddress());
	}
}
