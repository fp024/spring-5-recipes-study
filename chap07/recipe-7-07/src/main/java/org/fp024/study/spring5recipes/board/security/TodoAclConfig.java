package org.fp024.study.spring5recipes.board.security;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@EnableCaching
@Configuration
public class TodoAclConfig {
  private final DataSource dataSource;

  public TodoAclConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  AclEntryVoter aclEntryVoter(AclService aclService) {
    return new AclEntryVoter(
        aclService,
        "ACL_MESSAGE_DELETE",
        new Permission[] {BasePermission.ADMINISTRATION, BasePermission.DELETE});
  }

  // ✨✨✨ 캐시가 트랜젝션내에서 실행되므로 TransactionAwareCacheManagerProxy를 반드시 사용해주자.
  //         정상적인 흐름에서는 문제가 나타니지 않을 수 있는데, 롤백 상황에서 DB는 롤백 되었는데,
  //         캐시는 그대로인 상태가 되는 문제가 있을 수 있음.
  @Bean
  CacheManager jCacheCacheManager() throws Exception {
    var factoryBean = new JCacheManagerFactoryBean();
    factoryBean.setCacheManagerUri(new ClassPathResource("ehcache.xml").getURI());
    factoryBean.afterPropertiesSet();

    var cacheManager = new JCacheCacheManager(Objects.requireNonNull(factoryBean.getObject()));
    return new TransactionAwareCacheManagerProxy(cacheManager);
  }

  @Bean
  AuditLogger auditLogger() {
    return new ConsoleAuditLogger();
  }

  @Bean
  PermissionGrantingStrategy permissionGrantingStrategy() {
    return new DefaultPermissionGrantingStrategy(auditLogger());
  }

  @Bean
  AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ADMIN"));
  }

  @Bean
  AclCache aclCache(CacheManager cacheManager) {
    return new SpringCacheBasedAclCache(
        cacheManager.getCache("aclCache"),
        permissionGrantingStrategy(),
        aclAuthorizationStrategy());
  }

  @Bean
  LookupStrategy lookupStrategy(AclCache aclCache) {
    return new BasicLookupStrategy(
        this.dataSource, aclCache, aclAuthorizationStrategy(), permissionGrantingStrategy());
  }

  @Bean
  JdbcMutableAclService aclService(LookupStrategy lookupStrategy, AclCache aclCache) {
    return new JdbcMutableAclService(this.dataSource, lookupStrategy, aclCache);
  }

  @Bean
  AclPermissionEvaluator permissionEvaluator(AclService aclService) {
    return new AclPermissionEvaluator(aclService);
  }

  // ✨`https://docs.spring.io/spring-security/reference/5.8/migration/servlet/authorization.html#servlet-replace-permissionevaluator-bean-with-methodsecurityexpression-handler
  //    @EnableMethodSecurity does not pick up a PermissionEvaluator.
  //    아래 내용을 추가하고 나니 ACL 동작하는 모습이 보인다.
  @Bean
  MethodSecurityExpressionHandler expressionHandler(AclPermissionEvaluator permissionEvaluator) {
    var expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(permissionEvaluator);
    return expressionHandler;
  }
}
