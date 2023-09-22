package org.fp024.study.spring5recipes.court.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Members {

  @XmlElement(name = "member")
  private List<Member> members = new ArrayList<>();

  public void addMembers(Collection<Member> members) {
    this.members.addAll(members);
  }
}
