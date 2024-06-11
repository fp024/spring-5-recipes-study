package org.fp024.study.spring5recipes.caching.config;

import org.fp024.study.spring5recipes.caching.repository.CustomerRepository;
import org.fp024.study.spring5recipes.caching.repository.MapBasedCustomerRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableCaching
public class CustomerConfiguration {

  @Bean
  RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.create(connectionFactory);
  }

  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration serverConf = new RedisStandaloneConfiguration();
    serverConf.setHostName("spring-5-recipes-redis-host");
    serverConf.setPort(6379);
    return new JedisConnectionFactory(serverConf);
  }

  @Bean
  CustomerRepository customerRepository() {
    return new MapBasedCustomerRepository();
  }
}
