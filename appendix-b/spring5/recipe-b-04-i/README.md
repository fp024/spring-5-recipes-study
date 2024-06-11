## 레시피 b-04-i 커스텀 키 생성기 구성하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-04-i**:  커스텀 키 생성기 구성하기

  

  




## 진행

* 설정 클래스를 CachingConfigurerSupport를 상속 받게 해서 keyGenerator() 오버라이드 해서 커스텀 키 생성기를 사용하게 하는 예제




## 의견

* 이번 예제는 그다지 시행착오는 없었다.

* Java 8에서 인터페이스에 default 메서드를 정의할 수 있어서, CachingConfigurerSupport 상속 없이 CachingConfigurer 만 구현해서 하면 될 것 같은데...  앞으로 CachingConfigurerSupport는 `@Depreacted`가 붙을 수도 있을 것 같다.

   



---

## 기타

* ...

  

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

