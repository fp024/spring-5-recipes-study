package org.fp024.study.spring5recipes.course.dao;

import java.util.List;
import org.fp024.study.spring5recipes.course.domain.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateCourseDao implements CourseDao {

  private final SessionFactory sessionFactory;

  public HibernateCourseDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Course store(Course course) {
    Session session = sessionFactory.openSession();
    Transaction tx = session.getTransaction();
    try {
      tx.begin();
      session.saveOrUpdate(course);
      tx.commit();
      return course;
    } catch (RuntimeException e) {
      tx.rollback();
      throw e;
    } finally {
      session.close();
    }
  }

  @Override
  public void delete(Long courseId) {
    Session session = sessionFactory.openSession();
    Transaction tx = session.getTransaction();
    try {
      tx.begin();
      Course course = session.get(Course.class, courseId);
      session.delete(course);
      tx.commit();
    } catch (RuntimeException e) {
      tx.rollback();
      throw e;
    } finally {
      session.close();
    }
  }

  @Override
  public Course findById(Long courseId) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Course.class, courseId);
    }
  }

  @Override
  public List<Course> findAll() {
    try (Session session = sessionFactory.openSession()) {
      return session
          .createQuery(
              """
              SELECT c
                FROM Course c
              """,
              Course.class)
          .list();
    }
  }
}
