package org.fp024.study.spring5recipes.reactive.court;

import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

public class WebFluxInitializer extends AbstractReactiveWebInitializer {

  @Override
  protected Class<?>[] getConfigClasses() {
    return new Class<?>[] {WebFluxConfiguration.class};
  }
}
