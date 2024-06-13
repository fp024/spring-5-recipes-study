package org.fp024.study.spring5recipes.caching.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

public class Customer implements Serializable {
  @Getter private final long id;

  @Getter @Setter private String name;

  public Customer(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("Customer [id=%d, name=%s]", this.id, this.name);
  }
}
