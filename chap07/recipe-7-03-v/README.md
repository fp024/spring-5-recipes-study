## 레시피 7-03-v 유저 인증하기 - LDAP 저장소 조회 결과에 따라 유저 인증하기

> 유저 인증 정보 저장소의 여려 종류를 알아본다.
>

### 이번 레시피에서 확인해야할  내용

* ✔ 인메모리 방식 

* ✔ DB 조회 방식 

* ✔ 패스워드 암호화 

* ✅ LDAP 저장소 
  * 저자님 하신대로 도커 사용해야 편할 것 같긴했는데.. 예제 프로젝트이므로 Embedded 방식으로 실행해도 될 것 같다.

* ⬛ 유저 세부 캐시하기 
  * 이 내용은 자세하게 경험 해보질 않았는데.. 확실하게 하자.
    

## 진행

우선 하고자 하는 목표대로 Embedded LDAP 서버를 프로젝트에 내장하여 동작하게 하는데는 성공했다.

### 추가 디펜던시

```groovy
  implementation "org.springframework.security:spring-security-ldap:${springSecurityVersion}"
  implementation "com.unboundid:unboundid-ldapsdk:${unboundidLdapSdkVersion}"
  // 예제 실행환경에서 embedded LDAP 서버를 실행시킬 것이므로 implementation으로 둔다.
  implementation "org.springframework.ldap:spring-ldap-test:${springLdapVersion}"
  implementation "commons-lang:commons-lang:${commonsLangVersion}"
```

* Embedded LDAP 실행을 위해서.. `unboundid-ldapsdk`, `spring-ldap-test`, `commons-lang` 을 추가했다.



### LDAP 서버에 기본 입력 데이터 수정 (server.ldif)

```
# TODO: 현재 LDAP 설정에서 다음 주석 내용을 사용하면 이미 서버에 존재한다는 예외 메시지가 노출됨.
# dn: dc=springrecipes,dc=com
# objectClass: top
# objectClass: domain
# dc: springrecipes
...
```

위의 문제가 있어서 맨 처음 4줄을 주석으로 변경했고,

프로젝트에서 암호 인코더를 DelegatingPasswordEncoder를 사용하고 있으므로..

암호 앞에 암호 타입 접두어를 붙일 필요가 있었다.

```
userPassword: {noop}admin
```

* 저자님이 언급하신 [LdapShaPasswordEncoder](https://github.com/spring-projects/spring-security/blob/5.8.8/crypto/src/main/java/org/springframework/security/crypto/password/LdapShaPasswordEncoder.java)는 Spring Security 5.8.8 에선 폐기된 상태이다. BCryptPasswordEncoder, ... , DelegatingPasswordEncoder 로 바꿔쓰는 것을 추천.. 암호 업그레이드를 지원하는 DelegatingPasswordEncoder 를 가장 추천함. 👍



### Java 설정

* TestContextSourceFactoryBean 만 빈으로 잘 설정해주면 되었다.
  * Embedded LDAP 서버에 관리자 암호를 EmbeddedLdapServer에 정의된 대로 넣어줘야함.
    * https://github.com/spring-projects/spring-ldap/blob/94420b597360f2a7ee0424dadcb065c77ef908a2/test-support/src/main/java/org/springframework/ldap/test/unboundid/EmbeddedLdapServer.java#L43
  * https://docs.spring.io/spring-ldap/reference/testing.html 참조

```java

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  TestContextSourceFactoryBean contextSource() {
    TestContextSourceFactoryBean factory = new TestContextSourceFactoryBean();
    factory.setDefaultPartitionName("springrecipes");
    // factory.setBaseOnTarget(true);
    factory.setLdifFile(new ClassPathResource("server.ldif"));
    factory.setDefaultPartitionSuffix("dc=springrecipes,dc=com"); // ✨ EmbeddedLdapServer 에 미리 정의된 관리자 계정/비밀번호 설정
    factory.setPrincipal("uid=admin,ou=system");
    factory.setPassword("secret");
    factory.setPort(33389);
    return factory;
  }

  @Autowired
  void ldapSetting(AuthenticationManagerBuilder auth) throws Exception {
    auth.ldapAuthentication()
        .contextSource()
        .url("ldap://localhost:33389/dc=springrecipes,dc=com")
        .managerDn("uid=admin,ou=system") // ✨ EmbeddedLdapServer 에 미리 정의된 관리자 계정/비밀번호 설정
        .managerPassword("secret")
        .and()
        .userSearchFilter("uid={0}")
        .userSearchBase("ou=people")
        .groupSearchFilter("member={0}")
        .groupSearchBase("ou=groups")
        .passwordCompare()
        .passwordEncoder(passwordEncoder())
        .passwordAttribute("userPassword");
  }
```

이렇게 했을 때.. admin, user1로 정상 로그인된다.

권한도 정상동작하기 위해서는 rolePrefix를 ""로 해줄 필요가 있었음.. 기본값이 "ROLE_" 이였다.

```java
.rolePrefix("")
    
...
// 권한을 설정하는 부분
.requestMatchers(antMatcher(HttpMethod.DELETE, "/todos/*"))
.hasAuthority("ADMIN") // hasRole을 써도된다. 점점 Role 권장한다고 들은 것 같은데...😅
```

* 대소문자는 구분하지 않았다. server.ldif에는 권한이 소문자로 정의되어있어도 잘동작함.



### 리멤버 미 문제

```
        // .rememberMe(withDefaults())
        // ✨ TODO: LDAP 전역 설정을 했을 때, 어떻게 UserDetails를 넣어줄 수 있을지 지금은 모르겠다.
```

* [ ] TODO: 이건 어떤식으로 해결해야할지 잘 모르겠음... JdbcUserDetailsManager의 경우는 빈선언하기가 간단한데..  LdapUserDetailsManager의 경우는 뭔가 들어갈게 많음.. 




## 의견

* Embedded LDAP 설정이 너무 힘들었음..😂



---

## 기타

### 스프링 버전관리를 명확하기 위해서, 다음 처럼 BOM을 설정해주는 것도 괜찮을 것 같다.

```groovy
id "io.spring.dependency-management" version "${springDependencyManagementPluginVersion}"
...
dependencyManagement {
  imports {
    mavenBom "org.springframework:spring-framework-bom:${springVersion}"
  }
}
```

* spring-ldap-test 만 최신으로 올려서 테스트하려다 버전이 뒤죽박죽되는 상황이 보여서 이번에 넣어봄.



## 정오표

* ...
  


---

## JavaDocs

* ...
