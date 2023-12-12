package org.fp024.study.spring5recipes.board;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.service.TodoService;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class TodoInitializer {
  private final TodoService messageBoardService;

  public TodoInitializer(
      TodoService messageBoardService,
      DataSource dataSource,
      ResourceDatabasePopulator resourceDatabasePopulator) {
    this.messageBoardService = messageBoardService;
    resourceDatabasePopulator.execute(dataSource);
  }

  @PostConstruct
  public void setup() {
    LOGGER.info("### todo init setup ###");
    try {
      // user00의 초기 todo 데이터 입력
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  "user00", null, List.of(new SimpleGrantedAuthority("USER"))));

      Todo todo = new Todo();
      todo.setOwner("user00");
      todo.setDescription("Finish Spring Recipes - Security Chapter");

      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("user00");
      todo.setDescription("Get Milk & Eggs");
      todo.setCompleted(true);
      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("user00");
      todo.setDescription("Call parents.");

      messageBoardService.save(todo);

      // user01의 초기 todo 데이터 입력
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  "user01", null, List.of(new SimpleGrantedAuthority("USER"))));

      todo = new Todo();
      todo.setOwner("user01");
      todo.setDescription("스프링 시큐리티 챕터 끝내기!");

      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("user01");
      todo.setDescription("우유와 계란 구입");
      todo.setCompleted(true);
      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("user01");
      todo.setDescription("부모님께 전화하기");

      messageBoardService.save(todo);
    } finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}
