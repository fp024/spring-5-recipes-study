package org.fp024.study.spring5recipes.reactive.court;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  // 중계기 구성
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // topic으로 시작하는 메시지는 중계기로...
    registry.enableSimpleBroker("/topic");

    // app으로 시작하는 메시지는 @MessageMapping을 붙인 핸들러 메시지로...
    // app.js에서 호출할 때..
    // ws.send("/app/echo", message); 이런 식으로 호출하고 있다.
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/echo-endpoint");
  }
}
