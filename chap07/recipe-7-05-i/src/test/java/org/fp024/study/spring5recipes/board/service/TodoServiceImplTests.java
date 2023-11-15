package org.fp024.study.spring5recipes.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.fp024.study.spring5recipes.board.config.TodoRootConfig;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.security.TodoSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

/*
 서비스 메서드 호출 보호가 설정되어있으므로
*/
@SpringJUnitConfig(classes = {TodoRootConfig.class, TodoSecurityConfig.class})
@WithMockUser(
    value = "user00",
    authorities = {"USER"})
class TodoServiceImplTests {
  @Autowired private TodoService service;

  @Test
  void listTodos() {
    List<Todo> result = service.listTodos();
    assertThat(result).isNotEmpty();
  }

  @Transactional
  @Test
  void save() {
    Todo todo = new Todo();
    todo.setOwner("user01@user01.net");
    todo.setDescription("user01의 테스트");
    service.save(todo);
  }

  @Transactional
  @Test
  void complete() {
    service.complete(1L);
  }

  /*
   삭제는 ADMIN 권한만 가능
  */
  @Test
  @Transactional
  @WithMockUser(
      value = "admin",
      authorities = {"ADMIN"})
  void remove() {
    service.remove(1L);
  }

  @Test
  void findById() {
    Todo result = service.findById(1L);
    assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
  }
}
