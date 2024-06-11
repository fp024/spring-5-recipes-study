## 레시피 b-01-i Ehcache로 캐싱 구현하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-01-i**:  캐싱이 필요한 예제 준비

* ⬜ ...

  



## 진행

수행하는데 시간이 걸리는 계산을 똑같은 인자로 10번 실행

계산 메서드에는 ThreadSleep으로 500밀리초의 지연을 넣음.


* 예제 프로그램 실행

  ```bash
  gradle clean run
  ```

* 실행 결과

  ```
  ❯ gradle clean run
  Starting a Gradle Daemon, 1 busy and 4 incompatible and 5 stopped Daemons could not be reused, use --status for details
  
  > Task :run
  65536
  Took: 507
  65536
  Took: 508
  65536
  Took: 512
  65536
  Took: 501
  65536
  Took: 500
  65536
  Took: 500
  65536
  Took: 501
  65536
  Took: 510
  65536
  Took: 515
  65536
  Took: 514
  
  BUILD SUCCESSFUL in 14s
  5 actionable tasks: 4 executed, 1 up-to-date
  ❯
  ```

  

## 의견

* 다음 예제가 지연 메서드에 캐싱 적용하는 것이겠다. 👍




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

