package org.fp024.study.spring5recipes.board.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/todos")
public class TodoController {

  private final TodoService todoService;

  @GetMapping
  public String list(Model model) {
    List<Todo> todos = todoService.listTodos();
    model.addAttribute("todos", todos);
    return "todos";
  }

  @GetMapping("/new")
  public String create(Model model) {
    model.addAttribute("todo", new Todo());
    return "todo-create";
  }

  @PostMapping
  public String newMessage(@ModelAttribute @Valid Todo todo, BindingResult errors) {

    if (errors.hasErrors()) {
      return "todo-create";
    }
    String owner = "marten@apressmedia.net";
    todo.setOwner(owner);
    todoService.save(todo);
    return "redirect:/todos";
  }

  @PutMapping("/{todoId}/completed")
  public String complete(@PathVariable("todoId") long todoId) {
    this.todoService.complete(todoId);
    return "redirect:/todos";
  }

  @DeleteMapping("/{todoId}")
  public String delete(@PathVariable("todoId") long todoId) {
    this.todoService.remove(todoId);
    return "redirect:/todos";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // 컨트롤러와 서비스에서 id와 owner 필드를 제어하므로 바인드 하지 않음.
    binder.setDisallowedFields("id", "owner");
  }
}
