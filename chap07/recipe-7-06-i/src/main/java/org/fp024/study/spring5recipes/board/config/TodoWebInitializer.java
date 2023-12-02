package org.fp024.study.spring5recipes.board.config;

import org.fp024.study.spring5recipes.board.security.TodoSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TodoWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {TodoRootConfig.class, TodoWebConfig.class, TodoSecurityConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
