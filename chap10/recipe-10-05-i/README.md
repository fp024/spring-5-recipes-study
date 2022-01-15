## 레시피 10-05-i @Transactional을 붙여 선언적으로 트랜잭션 관리하기

* @Transactional 사용 예제

  * 설정 클래스에 @EnableTransactionManagement 붙임

    * 어드바이스 모드 기본값:  PROXY

      ```
      트랜잭션 어드바이스를 적용하는 방법을 나타냅니다.
      
      기본값은 AdviceMode.PROXY입니다. 프록시 모드에서는 프록시를 통해서만 호출을 가로챌 할 수 있습니다. 같은 클래스 내의 로컬 호출은 그런 식으로 가로챌 수 없습니다. Spring의 인터셉터가 그러한 런타임 시나리오에 대해 작동하지 않기 때문에 로컬 호출 내에서 그러한 메서드에 대한 Transactional 어노테이션은 무시됩니다. 더 고급 인터샙션 모드를 사용하려면 이것을 AdviceMode.ASPECTJ로 전환하는 것이 좋습니다. 기본값: AdviceMode.PROXY
      ```

      

    * proxyTargetClass 기본값: false

      ```
      표준 Java 인터페이스 기반 프록시(false)와 반대로 서브클래스 기반(CGLIB) 프록시를 생성할지(true) 여부를 나타냅니다. 기본값은 false입니다. mode()가 AdviceMode.PROXY로 설정된 경우에만 적용 가능합니다.
      ```

    * order 기본값: Ordered.LOWEST_PRECEDENCE

      ```
      특정 조인포인트에 여러 개의 어드바이스가 적용될 때 트랜잭션 어드바이저의 실행 순서를 나타낸다.
      ```

  * 대상 메서드에 @Transactional 붙임

  

* 인터페이스의  클래스/메서드 레벨에 @Transactional을 붙일 수는 있지만 클래스 기반 프록시(CGLIB)에는 제대로 동작하지 않을 수 있으니 권장하지 않음.

  

  
