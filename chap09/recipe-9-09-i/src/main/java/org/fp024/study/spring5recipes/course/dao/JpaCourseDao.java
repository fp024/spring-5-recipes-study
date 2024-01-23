package org.fp024.study.spring5recipes.course.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.fp024.study.spring5recipes.course.domain.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaCourseDao implements CourseDao {

  @PersistenceContext private EntityManager entityManager;

  @Transactional
  public Course store(Course course) {
    return entityManager.merge(course);
  }

  @Transactional
  public void delete(Long courseId) {
    Course course = entityManager.find(Course.class, courseId);
    entityManager.remove(course);
  }

  @Transactional(readOnly = true)
  public Course findById(Long courseId) {
    return entityManager.find(Course.class, courseId);
  }

  @Transactional(readOnly = true)
  public List<Course> findAll() {
    TypedQuery<Course> query =
        entityManager.createQuery(
            """
            SELECT c
              FROM Course c
            """, Course.class);
    return query.getResultList();
  }
}
