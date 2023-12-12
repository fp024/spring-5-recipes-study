package org.fp024.study.spring5recipes.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.fp024.study.spring5recipes.board.config.TodoRootConfig;
import org.fp024.study.spring5recipes.board.config.TodoWebConfig;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.security.TodoAclConfig;
import org.fp024.study.spring5recipes.board.security.TodoSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

/*
 서비스 메서드 호출 보호가 설정되어있으므로 Mock 유저 설정이 필요하다.
*/
@SpringJUnitWebConfig(
    classes = {
      TodoRootConfig.class, //
      TodoWebConfig.class,
      TodoSecurityConfig.class,
      TodoAclConfig.class
    })
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
      authorities = {"ADMIN", "USER"})
  void remove() {
    service.remove(1L);
  }

  /*
   다른 사람의 Todo를 조회하려 했기 때문에 예외가 발생
  */
  @WithMockUser(
      value = "marten@apressmedia.net",
      authorities = {"USER"})
  @Test
  void findOthersTodosById() {
    assertThatThrownBy(() -> service.findById(1L))
        .isInstanceOf(AccessDeniedException.class)
        .hasMessage("Access Denied");
  }

  /*
   자기 자신의 Todo를 조회했으므로 정상 종료
  */
  @Test
  void findOwnTodosById() {
    Todo result = service.findById(1L);
    assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
  }

  /*
   admin 권한은 다른 사람 Todo를 조회가 가능
  */
  @WithMockUser(
      value = "admin",
      authorities = {"ADMIN", "USER"})
  @Test
  void findOthersTodosByIdUseAdminAuthority() {
    Todo result = service.findById(1L);
    assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
  }
}
