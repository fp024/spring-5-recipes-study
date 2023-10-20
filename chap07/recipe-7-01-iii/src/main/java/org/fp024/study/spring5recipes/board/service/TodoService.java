package org.fp024.study.spring5recipes.board.service;

import java.util.List;
import org.fp024.study.spring5recipes.board.domain.Todo;

public interface TodoService {

  List<Todo> listTodos();

  void save(Todo todo);

  void complete(long id);

  void remove(long id);

  Todo findById(long id);
}
