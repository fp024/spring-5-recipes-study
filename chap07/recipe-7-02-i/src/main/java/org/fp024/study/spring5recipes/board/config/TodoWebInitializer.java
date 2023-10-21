package org.fp024.study.spring5recipes.board.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TodoWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[0];
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {TodoWebConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
