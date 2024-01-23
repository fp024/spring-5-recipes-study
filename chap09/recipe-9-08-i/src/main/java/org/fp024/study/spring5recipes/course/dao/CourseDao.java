package org.fp024.study.spring5recipes.course.dao;

import java.util.List;
import org.fp024.study.spring5recipes.course.domain.Course;

public interface CourseDao {

  Course store(Course course);

  void delete(Long courseId);

  Course findById(Long courseId);

  List<Course> findAll();
}
