## 레시피 b-07-i 레디스를 캐시 공급자로 활용하기

> ...

### 이번 레시피에서 확인해야할  내용

* ✅ **b-07-i**:  레디스를 캐시 공급자로 활용하기

  

  


## 진행

일단 로컬 도커로 레디스를 켜보자!

```bash
# 저자님 버전
docker run --name sr4-redis -p 6379:6379 -d redis:3.2-alpine
```

```bash
# 2024년 6월 기준 최신 버전으로 올려봤다.
docker run --name redis-7.x -p 6379:6379 -d redis:7.0.15-alpine
```



* 디펜던시 추가

  ```groovy
  implementation 'redis.clients:jedis:3.10.0'
  ```

  * 레디스에 연결하기위한 Java 환경 드라이버
  * Spring Data Redis 2.7.18에 선택적 디펜던시 걸린 버전과 비교해서 현시점 메이저 버전 대에서 최신으로 추가

* 레디스 서버에 대한 호스트 네임 지정 (host 파일 수정)

  ```
  {Redis가 실행중인 IP주소}      spring-5-recipes-redis-host
  ```

* 변경된 호스트 이름에 맞게 redisConnectionFactory()의 내용 변경

  ```java
    @Bean
    RedisConnectionFactory redisConnectionFactory() {
      RedisStandaloneConfiguration serverConf = new RedisStandaloneConfiguration();
      serverConf.setHostName("spring-5-recipes-redis-host");
      serverConf.setPort(6379);
      return new JedisConnectionFactory(serverConf);
    }
  ```

  

#### 실행결과

```
>gradle clean run

> Task :run
Get 'Unknown Customer' (result) : null
Create new Customer (result) : Customer [id=1, name=Marten Deinum]
Get 'New Customer 1' (result) : Customer [id=1, name=Marten Deinum]
Get 'New Customer 2' (result) : Customer [id=1, name=Marten Deinum]
Get 'Updated Customer 1' (result) : Customer [id=1, name=Josh Long]
Get 'Updated Customer 2' (result) : Customer [id=1, name=Josh Long]
Get 'Deleted Customer 1' (result) : null
Get 'Deleted Customer 2' (result) : null

StopWatch 'Cache Evict and Put': running time = 2155995600 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
582526500  027%  Get 'Unknown Customer'       // (1)
021274500  001%  Create New Customer          // (2)
003812300  000%  Get 'New Customer 1'         // (3)
000900900  000%  Get 'New Customer 2'         // (4)
004099900  000%  Update Customer              // (5)
515742900  024%  Get 'Updated Customer 1'     // (6)
000708200  000%  Get 'Updated Customer 2'     // (7)
001021100  000%  Remove Customer              // (8)
512044500  024%  Get 'Deleted Customer 1'     // (9)
513864800  024%  Get 'Deleted Customer 2'     // (10)

BUILD SUCCESSFUL in 5s
5 actionable tasks: 5 executed
>
```

실행 결과는 Ehcache를 사용한 것과 흐름의 차이는 없다. 

1. 없는 유저 조회 지연 있음.
2. 새로운 유저 생성, 이 때 캐시가 같이 생성됨
3. 새로운 유저 조회 (캐시된 내용을 읽음)
4. 한번더 새로운 유저 조회 (캐시된 내용을 읽음)
5. 새로운 유저를 업데이트 (이때 캐시가 취소됨)
6. 수정된 새로운 유저 조회 (실제 조회 후 캐시가 됨)
7. (6)에서 캐시가 되었기 때문에 캐시된 내용 조회
8. 유저를 삭제하여 실제 데이터 및 캐시도 같이 삭제
9. 없는 유저 조회 (실제 조회, null 결과는 캐시하지 않음)
10. 없는 유저 조회 (실제 조회, null 결과는 캐시하지 않음)




## 의견

* 그래도 좋은점은 서버가 여러대일 때 Ehcache의 경우 클러스터 설정을 하지 않는 이상 동기화가 필요할 경우 애매한데.. Redis는 모든 서버가 하나의 레디스를 바라보고 있으므로 동기화 걱정은 별로 없을 것 같다.
* 저자님 github를 보면 **cache-client.xml**라는 파일이 있는데, 이것은 예제 실행에 필요가 없는 것 같다.
  * https://github.com/Apress/spring-5-recipes/blob/master/spring-recipes-4th/appendix-b/recipe_b_7_i/src/main/resources/cache-client.xml
  * spring-gemfire란 것도 왠지 Vmware에서 제공하는 레디스 상품 같음...?




이제 부록 B도 한번 다 보았다. 도움이 되는 내용이였다. 👍👍👍




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

