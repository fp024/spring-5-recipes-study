## 레시피 3-09-i 컨트롤러에서 폼 처리하기 

> [3-08-iii](../recipe-3-08-iii)  예제에서 이미 Form 페이지가 있고, Spring Form 테그도 쓰고 있긴해서, 특별히 해줄일이 없긴한데.. 
>
> Validator 동작 확인하고, webjars BootStrap을 적용해서 간단하게 디자인을 바꿔보자.

### 이번 레시피에서 확인해야할  내용

* ...

  

## 진행

### 1. Bootstrap 스타일 적용

좀 보기 좋게 되긴 했다... 😂

![image-20230914031150724](doc-resources/image-20230914031150724.png)









## 의견

* 이번 예제는 



## 기타

* 테스트 환경에서 Hibernate Validator의 동작을 확인하기위해서는 아래 디펜던시가 필요함.

  ```groovy
  testRuntimeOnly 'org.glassfish:javax.el:3.0.1-b12'
  ```

  * https://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single/#validator-gettingstarted-uel
