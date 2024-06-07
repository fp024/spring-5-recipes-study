package org.fp024.study.spring5recipes.caching;

import org.fp024.study.spring5recipes.caching.domain.Customer;
import org.fp024.study.spring5recipes.caching.repository.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Configuration
@ComponentScan
public class App {

  private final CustomerRepository customerRepository;

  public App(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(App.class)) {
      context.getBean(App.class).run();
    }
  }

  public void run() {
    StopWatch sw = new StopWatch("Cache Evict and Put");

    // (1)
    sw.start("Get 'Unknown Customer'");
    Customer customer = customerRepository.find(1L);
    System.out.println("Get 'Unknown Customer' (result) : " + customer);
    sw.stop();

    // (2)
    sw.start("Create New Customer");
    customer = customerRepository.create("Marten Deinum");
    System.out.println("Create new Customer (result) : " + customer);
    sw.stop();

    long customerId = customer.getId();

    // (2)
    sw.start("Get 'New Customer 1'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'New Customer 1' (result) : " + customer);
    sw.stop();

    // (3)
    sw.start("Get 'New Customer 2'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'New Customer 2' (result) : " + customer);
    sw.stop();

    // (4)
    sw.start("Update Customer");
    customer.setName("Josh Long");
    customerRepository.update(customer);
    sw.stop();

    // (5)
    sw.start("Get 'Updated Customer 1'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'Updated Customer 1' (result) : " + customer);
    sw.stop();

    // (6)
    sw.start("Get 'Updated Customer 2'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'Updated Customer 2' (result) : " + customer);
    sw.stop();

    // (7)
    sw.start("Remove Customer");
    customerRepository.remove(customer.getId());
    sw.stop();

    // (8)
    sw.start("Get 'Deleted Customer 1'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'Deleted Customer 1' (result) : " + customer);
    sw.stop();

    // (9)
    sw.start("Get 'Deleted Customer 2'");
    customer = customerRepository.find(customerId);
    System.out.println("Get 'Deleted Customer 2' (result) : " + customer);
    sw.stop();

    System.out.println();
    System.out.println(sw.prettyPrint());
  }
}
