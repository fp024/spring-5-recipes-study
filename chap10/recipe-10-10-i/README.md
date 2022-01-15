## 레시피 10-10-i 로드타임 위빙을 이용해 트랜젝션 관리하기



* 예제의 동작이 조금 이상한데... 잔액이 부족하여 예외가 발생하여, 재고 컬럼이 롤백이 일어나야하는데, 롤백이 안일어난다. 😥

* 설정에서 아래 부분을 PROXY로 바꿔주면 정상 롤백되긴하였다.

  ```java
  @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
  // -->
  @EnableTransactionManagement(mode = AdviceMode.PROXY)
  ```

* 이후에는 `@EnableAspectJAutoProxy` 를 사용하고 `-javaagent:${ASPECTJ_WEAVER_PATH}` 를 JVM Argument로 넣어주었는데, 이때는 잘 동작하였다.

* 그외에 aop.xml을 추가해서 진행 로그를 더 볼 수 있게하고, 위빙(Weaving) 범위를 제한했다.

  * 위빙: 한 어드바이스 로직을 타겟에 적용하는 것

    ```xml
    <!DOCTYPE aspectj PUBLIC "-//AspectJ//DTD//EN"
            "http://www.eclipse.org/aspectj/dtd/aspectj.dtd">
    
    <aspectj>
        <weaver options="-verbose -showWeaveInfo">
            <include within="org.fp024.study.spring5recipes.bookshop.*"/>
        </weaver>
    </aspectj>
    ```

    

* 위빙 로그

  ```
  [AppClassLoader@383534aa] weaveinfo Join point 'method-execution(void org.fp024.study.spring5recipes.bookshop.JdbcBookShop.purchase(java.lang.String, java.lang.String))' in Type 'org.fp024.study.spring5recipes.bookshop.JdbcBookShop' (JdbcBookShop.java:16) advised by around advice from 'org.springframework.transaction.aspectj.AnnotationTransactionAspect' (AbstractTransactionAspect.aj:67) 
  [AppClassLoader@383534aa] weaveinfo Join point 'method-execution(void org.fp024.study.spring5recipes.bookshop.JdbcBookShop.increaseStock(java.lang.String, int))' in Type 'org.fp024.study.spring5recipes.bookshop.JdbcBookShop' (JdbcBookShop.java:29) advised by around advice from 'org.springframework.transaction.aspectj.AnnotationTransactionAspect' (AbstractTransactionAspect.aj:67)
  [AppClassLoader@383534aa] weaveinfo Join point 'method-execution(int org.fp024.study.spring5recipes.bookshop.JdbcBookShop.checkStock(java.lang.String))' in Type 'org.fp024.study.spring5recipes.bookshop.JdbcBookShop' (JdbcBookShop.java:43) advised by around advice from 'org.springframework.transaction.aspectj.AnnotationTransactionAspect' (AbstractTransactionAspect.aj:67)
  ```

  위의 로그 처럼 어떤 클래스의 어떤 메서드가 위빙되었다는 로그가 나와야하는데,  

  https://github.com/Apress/spring-5-recipes/tree/master/spring-recipes-4th/ch10/recipe_10_10_i 예제로는 정상 동작하지 않았다.

  
