package org.fp024.study.spring5recipes.court.domain;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlRootElement
public class Member {

  private String name;
  private String phone;
  private String email;
}
