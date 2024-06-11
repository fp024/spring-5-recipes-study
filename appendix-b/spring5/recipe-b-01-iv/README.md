## 레시피 b-01-iv Ehcache로 캐싱 구현하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✔ **b-01-i**:  캐싱이 필요한 예제 준비

* ✔ **b-01-ii**:  스프링 없이 Ehcache 적용 

* ✔ **b-01-iii**:  스프링으로 Ehcache 사용

* ✅ **b-01-iv**:  스프링으로 Ehcache 구성

  



## 진행

이번에는 진짜 스프링으로 Ehcache 구성하는 예제

* 디펜던시 추가

  ```groovy
  implementation 'org.springframework:spring-context-support'
  ```

  


## 의견

* 구성 클래스에서는 net.sf 로 시작하는 Ehcache의 클레스가 제거가 되었지만 아직 사용처에서 완전히 제거되진 않았다.
  * 이후 예제에서 스프링의 캐시 추상화 클래스로 제거할 것 같다.





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

