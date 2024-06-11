package org.fp024.study.spring5recipes.caching.repository;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.caching.config.CustomerConfiguration;
import org.fp024.study.spring5recipes.caching.domain.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(classes = CustomerConfiguration.class)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTests {

  @Autowired private CustomerRepository customerRepository;

  // (0) DB 저장 및 캐시도 같이 생성됨.
  @BeforeAll
  void init() {
    customerRepository.create("Jerry");
    customerRepository.create("Vega");
    customerRepository.create("Kent");
  }

  // (1) DB 저장 및 캐시도 같이 생성됨.
  @Order(1)
  @Test
  void testCreate() {
    Customer customer = customerRepository.create("Paul");
    assertThat(customer) //
        .isNotNull()
        .hasNoNullFieldsOrProperties();

    LOGGER.info("### Customer: {} ###", customer);
  }

  // (2) 캐시 내용 조회
  @Order(2)
  @Test
  void testFind() {
    Customer customer = customerRepository.find(1);

    assertThat(customer)
        .isNotNull()
        .hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("name", "Jerry");
  }

  // (3) 1번 유저 업데이트, 캐시 갱신
  @Order(3)
  @Test
  void testUpdate() {
    Customer customer = new Customer(1);
    customer.setName("Ran");

    customerRepository.update(customer);

    Customer ran = customerRepository.find(1);

    assertThat(ran) //
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", "Ran");
  }

  // (4) 1번 유저 삭제, 캐시도 삭제
  @Order(4)
  @Test
  void testRemove() {
    customerRepository.remove(1);

    Customer customer = customerRepository.find(1);

    assertThat(customer).isNull();
  }
}
