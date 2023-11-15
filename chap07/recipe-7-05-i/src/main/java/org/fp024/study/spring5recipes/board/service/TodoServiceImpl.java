package org.fp024.study.spring5recipes.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.repository.TodoRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  @Secured("USER")
  public List<Todo> listTodos() {
    return todoRepository.findAll();
  }

  @Override
  @Secured("USER")
  public void save(Todo todo) {
    this.todoRepository.save(todo);
  }

  @Override
  @Secured("USER")
  public void complete(long id) {
    Todo todo = findById(id);
    todo.setCompleted(true);
    todoRepository.save(todo);
  }

  @Override
  @Secured({"ADMIN"})
  public void remove(long id) {
    todoRepository.remove(id);
  }

  @Override
  @Secured("USER")
  public Todo findById(long id) {
    return todoRepository.findOne(id);
  }
}
