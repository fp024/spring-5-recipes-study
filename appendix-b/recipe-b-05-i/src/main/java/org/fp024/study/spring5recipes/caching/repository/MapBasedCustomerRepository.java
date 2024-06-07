package org.fp024.study.spring5recipes.caching.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.fp024.study.spring5recipes.caching.domain.Customer;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
public class MapBasedCustomerRepository implements CustomerRepository {

  private final Map<Long, Customer> repository = new HashMap<>();

  @Override
  @Cacheable(value = "customers")
  public Customer find(long customerId) {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      LOGGER.warn("Interrupted!", e);
      /* 중단하기 전에 처리해야 하는 모든 작업을 정리.  */
      Thread.currentThread().interrupt();
    }
    return repository.get(customerId);
  }

  @Override
  public Customer create(String name) {
    long id = UUID.randomUUID().getMostSignificantBits();
    Customer customer = new Customer(id);
    customer.setName(name);
    repository.put(id, customer);
    return customer;
  }

  @Override
  public void update(Customer customer) {
    repository.put(customer.getId(), customer);
  }

  @Override
  public void remove(long customerId) {
    repository.remove(customerId);
  }
}
