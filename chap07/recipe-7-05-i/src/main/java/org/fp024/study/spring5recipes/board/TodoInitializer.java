package org.fp024.study.spring5recipes.board;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.service.TodoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class TodoInitializer {

  private final TodoService messageBoardService;

  @PostConstruct
  public void setup() {
    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            "user", null, List.of(new SimpleGrantedAuthority("USER")));
    try {
      SecurityContextHolder.getContext().setAuthentication(auth);

      Todo todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Finish Spring Recipes - Security Chapter");

      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Get Milk & Eggs");
      todo.setCompleted(true);
      messageBoardService.save(todo);

      todo = new Todo();
      todo.setOwner("marten@apressmedia.net");
      todo.setDescription("Call parents.");

      messageBoardService.save(todo);
    } finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}
