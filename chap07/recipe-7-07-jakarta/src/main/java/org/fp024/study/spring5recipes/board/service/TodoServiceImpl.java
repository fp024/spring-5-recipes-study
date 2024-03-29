package org.fp024.study.spring5recipes.board.service;

import static org.springframework.security.acls.domain.BasePermission.DELETE;
import static org.springframework.security.acls.domain.BasePermission.READ;
import static org.springframework.security.acls.domain.BasePermission.WRITE;

import java.util.List;
import org.fp024.study.spring5recipes.board.domain.Todo;
import org.fp024.study.spring5recipes.board.repository.TodoRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  private final MutableAclService mutableAclService;

  public TodoServiceImpl(TodoRepository todoRepository, MutableAclService mutableAclService) {
    this.todoRepository = todoRepository;
    this.mutableAclService = mutableAclService;
  }

  @Override
  @PreAuthorize("hasAuthority('USER')")
  @PostFilter("hasAuthority('ADMIN') or hasPermission(filterObject, 'read')") // ✨
  public List<Todo> listTodos() {
    return todoRepository.findAll();
  }

  @Override
  @PreAuthorize("hasAuthority('USER')")
  public void save(Todo todo) {
    this.todoRepository.save(todo);

    // ✨
    ObjectIdentity oid = new ObjectIdentityImpl(Todo.class, todo.getId());
    MutableAcl acl = mutableAclService.createAcl(oid);

    acl.insertAce(0, READ, new PrincipalSid(todo.getOwner()), true);
    acl.insertAce(1, WRITE, new PrincipalSid(todo.getOwner()), true);
    acl.insertAce(2, DELETE, new PrincipalSid(todo.getOwner()), true);

    acl.insertAce(3, READ, new GrantedAuthoritySid("ADMIN"), true);
    acl.insertAce(4, WRITE, new GrantedAuthoritySid("ADMIN"), true);
    acl.insertAce(5, DELETE, new GrantedAuthoritySid("ADMIN"), true);
  }

  @Override
  @PreAuthorize(
      "hasPermission(#id, 'org.fp024.study.spring5recipes.board.domain.Todo', 'write')") // ✨
  public void complete(long id) {
    Todo todo = findById(id);
    todo.setCompleted(true);
    todoRepository.save(todo);
  }

  @Override
  @PreAuthorize(
      "hasPermission(#id, 'org.fp024.study.spring5recipes.board.domain.Todo', 'delete')") // ✨
  public void remove(long id) {
    todoRepository.remove(id);

    ObjectIdentity oid = new ObjectIdentityImpl(Todo.class, id);
    mutableAclService.deleteAcl(oid, false);
  }

  @Override
  @PostAuthorize("hasPermission(returnObject, 'read')") // ✨
  public Todo findById(long id) {
    return todoRepository.findOne(id);
  }
}
