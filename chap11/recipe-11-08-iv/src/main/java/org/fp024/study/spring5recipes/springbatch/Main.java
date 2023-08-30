package org.fp024.study.spring5recipes.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.springbatch.config.BatchConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(BatchConfiguration.class);
  }
}
