package org.fp024.study.spring5recipes.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.repository.TodoRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  @PreAuthorize("hasAuthority('USER')")
  @PostFilter("hasAuthority('ADMIN') or filterObject.owner == authentication.name")
  public List<Todo> listTodos() {
    return todoRepository.findAll();
  }

  @Override
  @PreAuthorize("hasAuthority('USER')")
  public void save(Todo todo) {
    this.todoRepository.save(todo);
  }

  @Override
  @PreAuthorize("hasAuthority('USER')")
  public void complete(long id) {
    Todo todo = findById(id);
    todo.setCompleted(true);
    todoRepository.save(todo);
  }

  @Override
  @PreAuthorize("hasAuthority('ADMIN')")
  public void remove(long id) {
    todoRepository.remove(id);
  }

  @Override
  @PreAuthorize("hasAuthority('USER')")
  @PostAuthorize("hasAuthority('ADMIN') or returnObject.owner == authentication.name")
  public Todo findById(long id) {
    return todoRepository.findOne(id);
  }
}
