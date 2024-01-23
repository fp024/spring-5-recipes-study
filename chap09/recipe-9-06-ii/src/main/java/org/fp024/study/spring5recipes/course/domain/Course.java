package org.fp024.study.spring5recipes.course.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", length = 100, nullable = false)
  private String title;

  @Column(name = "begin_date")
  private LocalDate beginDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "fee")
  private int fee;

  public Course(String title, LocalDate beginDate, LocalDate endDate, int fee) {
    this.title = title;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.fee = fee;
  }

  @Override
  public String toString() {
    return "Course ["
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", beginDate="
        + beginDate
        + ", endDate="
        + endDate
        + ", fee="
        + fee
        + ']';
  }
}
