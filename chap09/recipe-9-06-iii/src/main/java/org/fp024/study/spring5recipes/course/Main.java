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
    LOGGER.info("command args: {}", Arrays.toString(args));

    Course course = new Course();
    course.setTitle("Core Spring");
    course.setBeginDate(LocalDate.of(2007, 7, 1));
    course.setEndDate(LocalDate.of(2007, 8, 1));
    course.setFee(1000);

    System.out.println("\n### Course before persisting");
    System.out.println(course);

    // ✨ Hibernate를 직접 사용한 것과는 다르게, 파라미터로 넘긴 엔티티에 ID를 설정하지 않는다.
    Course persisted = courseDao.store(course);

    System.out.println("\n### Course after persisting");
    System.out.println(persisted);

    Long courseId = persisted.getId();
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
