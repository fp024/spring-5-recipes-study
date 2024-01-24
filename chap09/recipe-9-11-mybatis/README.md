## 레시피 9-11-mybatis JDBC 프로젝트의 MyBatis 전환

>  ✅ JDBC 템플릿을 사용했던 예제를 MyBatis로 변경
>

### 이번 레시피에서 확인해야할  내용

* 책에서는 MyBatis에 대해 다루진 않지만, 일반 JdbcTempate 프로젝트를 MyBatis로 바꿔보는 것도 괜찮을 것 같아서, 9-4-iii 예제를 MyBatis로 바꿔서 적용해보자!
  


## 진행

##### 레시피 9-11-mybatis

1. mybatis 에서 없는 내용을 조회 했을 때, 조회결과가 없다고 예외를 던지지는 않음.

2. 다중 INSERT 관련해서 지금 사용중인 DB가 동일하게 지원해서 테스트 성공은 했지만..

   ```xml
     <!-- MySQL, HSQLDB, H2 멀티 INSERT -->
     <insert id="insertVehicles" parameterType="list">
       INSERT INTO vehicle (vehicle_no, color, wheel, seat)
       VALUES
       <foreach item="item" collection="list" separator=",">
         (#{item.vehicleNo},
         #{item.color},
         #{item.wheel},
         #{item.seat})
       </foreach>
     </insert>
   ```

   지금은 운좋게 3개 DB 모두 다 지원이 되서 매퍼 파일을 하나로 두어도 문제는 없었지만... 만약 Oracle DB를 더 사용하게 된다면 멀티 INSERT 구문을 다른 방식으로 써야하므로 분기가 가능하도록 해야함.

   ```xml
     <!-- Oracle 멀티 INSERT -->
     <insert id="insertVehicles" parameterType="list">
       <foreach item="item" collection="list" open="INSERT ALL" separator=" "
         close="SELECT * FROM DUAL">
         INSERT INTO vehicle (vehicle_no, color, wheel, seat)
         VALUES (
           #{item.vehicleNo},
           #{item.color},
           #{item.wheel},
           #{item.seat}
         )
       </foreach>
     </insert>
   ```

   

구조를 좀 개선해서 Mapper를 직접 쓰지 않고 DAO를 두고 거기에서 Mapper를 참조해서 호출하게 함.

그리고 `insert(List<Vehicle> vehicles)` 에 대해서는 배치 `SqlSession`을 받아서 다음과 같이 수행해서 JdbcTemplate 에사 batchUpdate() 한 내용과 동일하게 수행되도록 했다..😊👍

```java
  @Override
  public void insert(List<Vehicle> vehicles) {
    try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
      vehicles.forEach(
          vehicle -> session.update(VehicleMapper.class.getName() + ".insert", vehicle));
      session.commit();
    }
  }
```

```
10:27:42.662 [main] DEBUG jdbc.sqltiming -  com.zaxxer.hikari.pool.ProxyStatement.executeBatch(ProxyStatement.java:127)
1. batching 3 statements:
1:  INSERT INTO vehicle (vehicle_no, color, wheel, seat)
    VALUES ('TEM2001', 'Yellow', 4, 6)
2:  INSERT INTO vehicle (vehicle_no, color, wheel, seat)
    VALUES ('TEM2002', 'Silver', 4, 4)
3:  INSERT INTO vehicle (vehicle_no, color, wheel, seat)
    VALUES ('TEM2003', 'White', 4, 2)
 {executed in 13 msec}
```

실행로그도  batch update로 잘 처리되었다. 😊




## 의견

* ...



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

* ✅
* ✔
* ⬜
* 💡
* ...

