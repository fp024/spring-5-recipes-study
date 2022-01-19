package org.fp024.study.spring5recipes.springbatch;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistration implements Serializable {

  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private String company;
  private String address;
  private String city;
  private String state;
  private String zip;
  private String county;
  private String url;
  private String phoneNumber;
  private String fax;
}
