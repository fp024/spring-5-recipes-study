package org.fp024.study.spring5recipes.reactive.court;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class ReactorNettyBootstrap {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(WebFluxConfiguration.class);
    HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();

    ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);

    DisposableServer server =
        HttpServer.create() //
            .host("localhost")
            .port(8080)
            .handle(adapter)
            .bindNow();

    server.onDispose().block();
  }
}
