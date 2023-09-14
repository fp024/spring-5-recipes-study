package org.fp024.study.spring5recipes.court.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CourtServletContainerInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {CourtConfiguration.class, I18NConfiguration.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
