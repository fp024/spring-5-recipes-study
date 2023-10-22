## 레시피 7-03-ii 유저 인증하기 - DB 조회 결과에 따라 유저 인증

> 유저 인증 정보 저장소의 여려 종류를 알아본다.
>

### 이번 레시피에서 확인해야할  내용

* ✔ 인메모리 방식 

* ✅ DB 조회 방식 

* ⬛ 패스워드 암호화 

* ⬛ LDAP 저장소 
  * 저자님 하신대로 도커 사용해야 편하겠다.

* ⬛ 유저 세부 캐시하기 
  * 이 내용은 자세하게 경험 해보질 않았는데.. 확실하게 하자.
    

## 진행

### JDBC 인증 메니저 사용 후, Remember-Me 동작 문제

JDBC 인증 메니저를 사용하기 위해 다음과 같이 설정할 수도 있는데...

```java
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth.jdbcAuthentication().dataSource(dataSource);
}
```

이 경우 아래의 예외가 나오면서 Remember-Me가 userDetailsService를 못찾는다.. 

```
Caused by: java.lang.IllegalStateException: userDetailsService cannot be null. Invoke RememberMeConfigurer#userDetailsService(UserDetailsService) or see its javadoc for alternative approaches.
```

다음 처럼 명시적으로 지정해줬을 때.. 정상 동작했다.

```java
  @Bean
  UserDetailsService userDetailsService() {
    return new JdbcUserDetailsManager(dataSource);
  }
```



### DataSource 빈을 SecurtyConfig에 넣어두긴 좀 그래서.. RootConfig로 분리했다.

* TodoRootConfig, TodoSecurityConfig, TodoWebConfig 3가지로 나눔.






## 의견

* ...



---

## 기타

* ...



## 정오표

* ...
  


---

## JavaDocs

* ...
