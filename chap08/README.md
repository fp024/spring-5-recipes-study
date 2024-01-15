# Chapter 8 스프링 모바일

> 스프링 모바일이 더이상 사용되고 있지 않은 상태여서,  이번 장도 간단히 읽어보기만 하였다.



### Spring Mobile 

* 메인 소개 페이지

  * https://spring.io/projects/spring-mobile/
  * 별 내용 없이 지원 종료되었다고 공지되어있다.

* GitHub 저장소

  * https://github.com/spring-attic/spring-mobile

  * https://github.com/spring-attic/spring-mobile/commits/master/

    * 커밋로그를 보면 2018년들어서 변화도 별로 없고, 프로젝트를 그만 두기로 한 것 같다. 😅

      

- [x] 간단하게 한번만 읽어보자!

> 한번 읽어봄. 😅, 분량도 적은 편이 였음...



### 책 코드

* 저자님
  * https://github.com/Apress/spring-5-recipes/tree/master/spring-recipes-4th/ch08

* 역자님
  * https://github.com/nililee/spring-5-recipes/tree/master/spring-recipes-4th/ch08





## 의견

예전에 업무에서 디바이스 구분하는 것을 UserAgent 값에 따라서 구분을 했던 것 같은데.. 까다로운 일이였다. 

이런 일을 해주는 것을 라이브러리로 제공해준 것 같은데....

* https://github.com/spring-attic/spring-mobile/tree/master/spring-mobile-device/src/main/java/org/springframework/mobile/device

나중에 직접 구현을 해야했을 때는 참고할 수 있을 것 같다.



#### 디바이스

* 디바이스 유틸리티
  * https://github.com/spring-attic/spring-mobile/blob/master/spring-mobile-device/src/main/java/org/springframework/mobile/device/DeviceUtils.java
* 디바이스 인터페이스
  * https://github.com/spring-attic/spring-mobile/blob/master/spring-mobile-device/src/main/java/org/springframework/mobile/device/Device.java

나도 디바이스 구분을 하는 도메인을 만들긴했던 것 같은데...😅



### 기기 정보에 따라 뷰 랜더링

* https://github.com/spring-attic/spring-mobile/blob/master/spring-mobile-device/src/main/java/org/springframework/mobile/device/view/LiteDeviceDelegatingViewResolver.java

감지된 디바이스에 따라 다른 뷰 랜더링...



### 기기 정보에 따라 페이지 리다이렉트

* `http://www.yourdomain.com` -> `http://m.yourdomain.com`
* `http://www.yourdomain.com` -> `http://www.yourdomain.com/mobile`
* ...
* https://github.com/spring-attic/spring-mobile/blob/2a7bee745911c45a458a59627a2679d01977353b/spring-mobile-device/src/main/java/org/springframework/mobile/device/switcher/SiteSwitcherHandlerInterceptor.java#L199



결국 이런 스프링에서 이런식으로 구현할 수 있다는 것을 보여준 것 같다.

시간이 지날때마다 모바일 환경의 변경이 워낙커서, 그 변경에 맞게 이 가이드 대로 개발자가 참고해서 바꿔서쓰라는 의미가 커서 왠지... 프로젝트가 중단된 것 같은..

그런 생각이 듬..



중단된 코드지만, 그래도 필요한 순간이 있을 때.. 참고용으로 도움이 많이 될 것 같다. 😊👍



## 진행간 이슈

* 진행 없음.



## 정오표

* ...
