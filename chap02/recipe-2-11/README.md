## 레시피 2-11 스프링 환경 및 프로파이마다 다른 POJO 로드하기

* 설정 클래스에 `@Profile("환경이름")`를 붙여서  프로필별 설정을 할 수 있음

* `-Dspring.profiles.active=환경이름1,환경이름2` 를 입력해서 JVM 환경변수를 설정하더라도 인식이 안되는 것 같다. 

  * 시스템 환경변수를 읽어서 컨텍스트에 수동으로 넣어주었음.

    ```java
    String[] profiles = ((String)context.getEnvironment().getSystemProperties().get("spring.profiles.active"))
                  .split(",");
    context.getEnvironment().setActiveProfiles(profiles);
    ```

    

* gradle 에서 직접 -D옵션을 주더라도 실행 클래스로 환경변수 전달이 안되는데, 아래와 같은 식으로 해결 했음

  ```groovy
  // 일반 함수 선언
  String getActiveProfiles() {
      def activeProfiles = System.getProperty("spring.active.profiles")
      // if (activeProfiles == null || activeProfiles.isEmpty()) {
      if (!activeProfiles?.trim()) {  // Groovy의 null or empty 체크
          logger.quiet("activeProfiles is null or empty: ${activeProfiles}")
          return 'global,winter'
      } else {
          logger.quiet("activeProfiles: ${activeProfiles}")
          return "${activeProfiles}"
      }
  }
  
  // 상수에 프로필 내용 할당
  final ACTIVE_PROFILES = getActiveProfiles();
  
  // 테스트 환경 프로필 설정
  test {
      systemProperty "spring.profiles.active", "${ACTIVE_PROFILES}"
      //...
  }
  
  // 실행환경 프로필 설정
  application {
      applicationDefaultJvmArgs = ["-Dspring.profiles.active=${ACTIVE_PROFILES}"]
      // ...
  }
  
  ```
  
  application 클래스에서 `spring.profiles.active`를 시스템 프로퍼티로 받아와서, applicationDefaultJvmArgs 에다 설정하는 식으로 진행했다.


  힘들긴 했는데, 이런식으로 변수 선언해서 코드를 구성할 수 있는 것이 좋아보인다. 😄



> 😂 안된다고 했던 부분은 왠지 내가 오타를 발생시켜서 그런 것 같음.
>
> * 잘못됨: `spring.active.profiles`
> * 올바름: `spring.profiles.active`
