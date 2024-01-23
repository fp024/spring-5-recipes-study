package org.fp024.study.spring5recipes.course.dao;

import org.fp024.study.spring5recipes.course.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {}
