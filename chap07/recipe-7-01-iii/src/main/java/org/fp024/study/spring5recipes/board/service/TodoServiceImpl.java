package org.fp024.study.spring5recipes.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  public List<Todo> listTodos() {
    return todoRepository.findAll();
  }

  @Override
  public void save(Todo todo) {
    this.todoRepository.save(todo);
  }

  @Override
  public void complete(long id) {
    Todo todo = findById(id);
    todo.setCompleted(true);
    todoRepository.save(todo);
  }

  @Override
  public void remove(long id) {
    todoRepository.remove(id);
  }

  @Override
  public Todo findById(long id) {
    return todoRepository.findOne(id);
  }
}
