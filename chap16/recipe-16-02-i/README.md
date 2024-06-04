## 레시피 16-02-i 단위/통합 테스트 작성하기

> 16장 코드들을 미리 보았는데, 아무래도 집필 시점에 JUnit 5가 나오질 않아서 JUnit 4 기준으로만 테스트를 만드신 것 같다. 이제 부터 JUnit 4 테스트 코드들은 JUnit 5로 바꿔가면서 진행하자!

### 이번 레시피에서 확인해야할  내용

* ✅ **16-02-i**: 단일 클래스에 대한 단위 테스트 작성하기

* ⬜ ...

  



## 진행

* ...






## 의견

* 저자님께서 JUnit 4의 `assertEquals(expected, actual)`메서드의 파라미터 값을 반대로 쓰신 경우가 많음 😅 그런데 이게 TestNG였다면 `assertEquals(actual, expected)`이여서 쓰신 것이 맞긴함...😅 TestNG를 주로 사용하시는 분이였나봄.

* 저자님께서 Account에 `private` 생성자를 만들어두셨는데... 

  ```java
  /** Default constructor because JPA demands it. */
  private Account() {
    this(null, 0.0d);
  }
  ```

  적어도 protected로 사용하시는 것이 나을 것 같다. 그런데, JPA를 사용하는 예제는 아님? 😅

  HashMap을 이용한 in-memory 리파지토리를 구현해두신거 JPA 코드로 바꾸려다 말았다..😅😅



* 검증 코드는 JUnit 5 + assertj의 assertThat()으로 바꿨다.

  


---

## 기타

### Mock과 Stub의 차이가 뭐니?

Mock과 Stub은 테스트 더블(Test Double)의 일종으로, 실제 객체를 테스트 환경에서 대체하기 위해 사용되는 객체들입니다. 그러나 두 용어는 서로 다른 목적과 사용 방법을 가지고 있습니다.

**Stub**은 테스트 중인 코드가 호출할 때 미리 준비된 응답을 제공하는 객체입니다. 주로 테스트 대상이 되는 코드가 의존하는 외부 시스템이나 컴포넌트를 대신하여, 테스트를 위해 고정된 데이터를 반환합니다. Stub은 테스트 중인 코드가 올바르게 동작하는지 검증하는 데에는 직접적으로 사용되지 않고, 테스트를 원활하게 진행하기 위한 수단으로 사용됩니다.

**Mock**은 Stub과 비슷하지만, 테스트 중인 코드가 특정한 방식으로 Mock 객체를 사용할 것이라는 기대(Expectation)를 가지고 있습니다. Mock 객체는 호출된 메서드, 전달된 매개변수, 호출 횟수 등을 검증할 수 있으며, 테스트 중인 코드가 이러한 기대를 충족하는지 여부를 확인하는 데 사용됩니다. 즉, Mock은 테스트 중인 코드의 행동을 검증하는 데 중점을 둡니다.

간단히 말해, **Stub**은 테스트를 위해 미리 정의된 응답을 제공하는 반면, **Mock**은 특정한 행동에 대한 기대를 설정하고 그 기대가 충족되었는지를 검증합니다. 따라서, Mock은 Stub보다 더 복잡한 행동을 검증할 수 있는 기능을 가지고 있습니다.

테스트를 설계할 때는 테스트의 목적에 따라 적절한 테스트 더블을 선택하는 것이 중요합니다.



### Collections.synchronizedMap(new HashMap<String, Account>()); 와 ConcurrentHashMap의 차이가 있니?

네, `Collections.synchronizedMap(new HashMap<String, Account>());`와 `ConcurrentHashMap` 사이에는 중요한 차이점이 있습니다. 두 구현 모두 스레드 안전성을 제공하지만, 내부 동작 방식과 성능에서 차이가 나타납니다.

`Collections.synchronizedMap`은 주어진 맵을 감싸서 모든 메서드 호출을 `synchronized` 블록으로 만듭니다. 이는 맵의 모든 작업이 스레드에 안전하게 실행되도록 보장하지만, 한 번에 하나의 스레드만 맵에 접근할 수 있기 때문에 동시성 수준이 낮습니다. 즉, 한 스레드가 맵을 사용하는 동안 다른 스레드는 대기해야 합니다.

반면, `ConcurrentHashMap`은 내부적으로 데이터를 여러 세그먼트로 나누어 관리합니다. 이를 통해 여러 스레드가 동시에 맵의 다른 부분을 읽거나 쓸 수 있도록 하여 높은 동시성을 제공합니다. `ConcurrentHashMap`은 읽기 작업을 락 없이 수행할 수 있으며, 쓰기 작업은 특정 세그먼트에 대해서만 락을 걸기 때문에 성능이 향상됩니다.

또한, `ConcurrentHashMap`은 `null` 값을 키나 값으로 허용하지 않으며, `Collections.synchronizedMap`은 `null`을 허용합니다. `ConcurrentHashMap`은 `ConcurrentModificationException`을 발생시키지 않는 반면, `Collections.synchronizedMap`을 사용할 때는 이 예외가 발생할 수 있습니다.

따라서, 높은 동시성과 성능이 필요한 경우 `ConcurrentHashMap`을 사용하는 것이 좋으며, 단순한 스레드 안전성만 필요한 경우에는 `Collections.synchronizedMap`을 사용할 수 있습니다. 



### JPA가 엔티티의 기본 생성자를 요구하잖아? 그런데 접근자가 private가 되어선 문제가 되는 경우가 있지 않니?

네, JPA는 엔티티의 기본 생성자를 요구합니다. 이는 JPA가 Reflection API를 사용하여 엔티티 객체를 동적으로 생성하고 관리하기 때문입니다. 기본 생성자는 JPA가 프록시 객체를 생성하거나 데이터베이스에서 엔티티를 복원할 때 필요합니다.

기본 생성자의 접근자가 private인 경우, JPA 구현체가 해당 생성자에 접근할 수 없어 문제가 발생할 수 있습니다. 이는 JPA가 엔티티 객체를 생성할 때 기본 생성자를 사용해야 하기 때문입니다. 따라서, 기본 생성자는 public 또는 protected 접근자를 가지고 있어야 합니다protected 접근자는 클래스 외부에서는 접근할 수 없지만, JPA와 같은 프레임워크에서는 엔티티 객체를 생성할 수 있게 해줍니다.

private 접근자를 사용하면 Reflection을 통한 객체 생성이 제한되므로, JPA 스펙에 따라 기본 생성자는 최소한 protected 접근자를 가져야 합니다. 이렇게 하면 JPA 구현체가 엔티티 객체를 안전하게 생성하고 관리할 수 있습니다.



### 그런데 JPA 구현체가 Hibernate라면 private 생성자가 동작하긴하지?

네, Hibernate는 JPA 구현체 중 하나이며, JPA 스펙에 따르면 엔티티 클래스는 `public` 또는 `protected` 접근자를 가진 기본 생성자를 가져야 합니다. 그러나 Hibernate는 이 규칙에 대해 좀 더 유연하며, 시스템의 `SecurityManager`가 접근성 설정을 변경하는 것을 허용한다면 `private` 접근자를 가진 기본 생성자도 사용할 수 있습니다.

이는 Hibernate가 내부적으로 Reflection을 사용하여 `private` 생성자에 접근할 수 있기 때문입니다. 그럼에도 불구하고, 일반적으로는 `protected` 접근자를 사용하는 것이 좋습니다. 이는 JPA 스펙을 준수하면서도 클래스의 캡슐화를 유지할 수 있는 방법입니다.

`private` 생성자를 사용할 경우, 프록시 생성과 같은 일부 Hibernate 기능이 영향을 받을 수 있으므로, 가능하면 `protected` 접근자를 사용하는 것이 안전한 방법입니다.







## 정오표

* ...
  


---

## JavaDocs

* ...



---

### 문서 사용 아이콘

* ✅
* ✔
* ⬜
* ...

