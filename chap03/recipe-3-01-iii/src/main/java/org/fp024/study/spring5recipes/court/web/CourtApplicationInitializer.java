package org.fp024.study.spring5recipes.court.web;

import org.fp024.study.spring5recipes.court.service.config.ServiceConfiguration;
import org.fp024.study.spring5recipes.court.web.config.WebConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CourtApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {ServiceConfiguration.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {WebConfiguration.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/", "/welcome"}; // 여기 /welcome 을 왜? 두셨지?
  }
}
