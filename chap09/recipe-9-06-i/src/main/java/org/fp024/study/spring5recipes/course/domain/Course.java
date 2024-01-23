package org.fp024.study.spring5recipes.course.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Course {

  private Long id;

  private String title;

  private LocalDate beginDate;

  private LocalDate endDate;

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
