package org.fp024.study.spring5recipes.board.config;

import org.fp024.study.spring5recipes.board.security.TodoAclConfig;
import org.fp024.study.spring5recipes.board.security.TodoSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TodoWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {
      TodoRootConfig.class, //
      TodoWebConfig.class,
      TodoSecurityConfig.class,
      TodoAclConfig.class
    };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
