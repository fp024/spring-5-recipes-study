package org.fp024.study.spring5recipes.course;

import java.time.LocalDate;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.course.dao.CourseDao;
import org.fp024.study.spring5recipes.course.domain.Course;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ComponentScan
@Configuration
public class Main {
  private final CourseDao courseDao;

  public Main(CourseDao courseDao) {
    this.courseDao = courseDao;
  }

  void run(String[] args) {
    LOGGER.info("commonad args: {}", Arrays.toString(args));

    Course course = new Course();
    course.setTitle("Core Spring");
    course.setBeginDate(LocalDate.of(2007, 7, 1));
    course.setEndDate(LocalDate.of(2007, 8, 1));
    course.setFee(1000);

    System.out.println("\n### Course before persisting");
    System.out.println(course);

    courseDao.store(course);

    System.out.println("\n### Course after persisting");
    System.out.println(course);

    Long courseId = course.getId();
    Course courseFromDb = courseDao.findById(courseId);

    System.out.println("\n### Course fresh from database");
    System.out.println(courseFromDb);

    courseDao.delete(courseId);
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Main.class)) {
      context
          .getBean(Main.class) //
          .run(args);
    }
  }
}
