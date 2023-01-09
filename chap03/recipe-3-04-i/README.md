## 레시피 3-4-i 유저 로케일 해석하기



## 진행

* language 파라미터 붙여서 정상적으로 로케일 처리가 되는지 확인함.
* 인터셉터 동작확인도 하였다.
* localeChangeInterceptor 의 등록도 InterceptorConfiguration 설정 클래스에 몰아둠.



## 이슈

* Cannot change HTTP Accept-Language header - use a different locale resolution strategy 예외 발생 문제
  * https://github.com/fp024/spring-5-recipes-study/issues/7
