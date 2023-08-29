package org.fp024.study.spring5recipes.springbatch.outer.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableBatchProcessing
@ToString
@Table(name = "USER_REGISTRATION")
public class UserRegistration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "COMPANY")
  private String company;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "CITY")
  private String city;

  @Column(name = "STATE")
  private String state;

  @Column(name = "ZIP")
  private String zip;

  @Column(name = "COUNTY")
  private String county;

  @Column(name = "URL")
  private String url;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "FAX")
  private String fax;

  @Column(name = "REGISTER_DATE")
  private LocalDate registerDate;
}
