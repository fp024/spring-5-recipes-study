package org.fp024.study.spring5recipes.course.config;

import org.fp024.study.spring5recipes.course.dao.CourseDao;
import org.fp024.study.spring5recipes.course.dao.HibernateCourseDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfigation {

  @Bean
  CourseDao courseDao(SessionFactory sessionFactory) {
    return new HibernateCourseDao(sessionFactory);
  }
}
