## 레시피 16-06-i 통합 테스트에서 트랜잭션 관리하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **16-06-i**: JUnit에서 테스트 컨텍스트 프레임워크의 트랜젝션 관리하기

* ✖️ **16-06-ii**: JUnit에서 테스트 컨텍스트 프레임워크의 트랜젝션 관리하기 - AbstractTransactionalJUnit4SpringContextTests

* ✅ **16-06-iii**: TestNG에서 테스트 컨텍스트 프레임워크의 트랜젝션 관리하기

  




## 진행

* **16-06-i**에서 이미 프로젝트 틀을 다 잡아놓아서, TestNG로만 잘 바꿨다. 😄



## 의견

* ...




---

## 기타

JUnit 5 였다면...  클래스에 다음과 같은 수식을 붙여서 default 프로필이 활성화 되었을 때만 테스트를 실행할 수 있는데...

```java
@EnabledIf("#{systemProperties['spring.profiles.active'].contains('default')}")
```

TestNG 에서는 따로 해줄 수 있는 게 없는 것 같다.. 😅

현시점에서는 in-mem으로 프로필을 지정해서 실행하면 JDBC 관련 설정이 활성화 되지 않은상태에서 AccountServiceTestNGContextTests를 실행하게 되서 오류가 발생함.

```bash
gradle clean test -Dspring.profiles.active=in-mem
```



그런데... 테스트 클래스에 다음을 붙여주면...

```java
@ActiveProfiles("default")
```

test 실행시 정한 프로필과 상관없이 무조건 default를 활성화해서 테스트를 실행시킨다.

애플리케이션 컨텍스트에서 활성화된 프로필을 가져와보면 default로 되어있는 것을 알 수 있음.

```
 02:27:10.253 [Test worker] INFO  org.fp024.study.spring5recipes.bank.service.AccountServiceTestNGContextTests - ### Current Profile: [default] ###
```





## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅: Current Done
* ✔: Done
* ⬜: TODO
* ✖️: Skip
* ...

