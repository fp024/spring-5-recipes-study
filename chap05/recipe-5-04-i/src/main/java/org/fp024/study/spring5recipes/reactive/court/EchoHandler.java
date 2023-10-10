package org.fp024.study.spring5recipes.reactive.court;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class EchoHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    session.sendMessage(new TextMessage("CONNECTION ESTABLISHED"));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    // 커넥션이 닫힌 상태여서 메시지를 보낼 수 없음.
    // session.sendMessage(new TextMessage("CONNECTION CLOSED"));

    // 그런데 특이한 점은 session은 아직 열려있음.
    LOGGER.info("### session is open: {} ###", session.isOpen());
    LOGGER.info("### CONNECTION CLOSED ###");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String msg = message.getPayload();
    session.sendMessage(new TextMessage("RECEIVED: " + msg));
  }
}
