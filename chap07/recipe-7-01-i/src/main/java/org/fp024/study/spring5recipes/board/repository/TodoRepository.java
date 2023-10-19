package org.fp024.study.spring5recipes.board.repository;

import java.util.List;
import org.fp024.study.spring5recipes.board.domain.Todo;

public interface TodoRepository {

  List<Todo> findAll();

  Todo findOne(long id);

  void remove(long id);

  Todo save(Todo todo);

  List<Todo> findByOwner(String author);
}
