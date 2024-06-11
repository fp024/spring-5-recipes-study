package org.fp024.study.spring5recipes.caching.repository;

import org.fp024.study.spring5recipes.caching.domain.Customer;

public interface CustomerRepository {

  Customer find(long customerId);

  Customer create(String name);

  void update(Customer customer);

  void remove(long customerId);
}
