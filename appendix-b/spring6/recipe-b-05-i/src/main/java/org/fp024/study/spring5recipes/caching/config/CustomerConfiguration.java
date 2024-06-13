package org.fp024.study.spring5recipes.caching.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import org.fp024.study.spring5recipes.caching.repository.CustomerRepository;
import org.fp024.study.spring5recipes.caching.repository.MapBasedCustomerRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CustomerConfiguration {

  @Bean
  CacheManager cacheManager() {
    return new JCacheCacheManager(Objects.requireNonNull(jCacheManagerFactoryBean().getObject()));
  }

  @Bean
  KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        return method.getName()
            + Arrays.stream(params).map(Object::toString).collect(Collectors.joining("^"));
      }
    };
  }

  @Bean
  JCacheManagerFactoryBean jCacheManagerFactoryBean() {
    try {
      JCacheManagerFactoryBean factory = new JCacheManagerFactoryBean();
      factory.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
      return factory;
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load Ehcache configuration", e);
    }
  }

  @Bean
  public CustomerRepository customerRepository() {
    return new MapBasedCustomerRepository();
  }
}
