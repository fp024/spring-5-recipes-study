# Chapter 9 데이터 액세스

> 이전 몇몇 장에서도 DB를 사용했었지만, 이번 9장은 DB를 본격적으로 쓰는 장인데, 어떻게 해야할까? 😅



## 준비

### DB

저자님은 [PostgreSQL](https://www.postgresql.org/)을 사용하시긴 했는데, 복잡하게 사용하시진 않았음, 도커 명령어로 컨테이너 받아서, Console로 접속해서 테이블 만드는 것을 하셨는데... 

이 장에서 PostgreSQL DB에 특화된 쿼리를 사용하진 않을 것으로 보여서, HSQLDB로 구성해도 문제는 없을 것 같다. HSQLDB로 바꿔두면 나중에 메모리 모드로 바꾸기도 편하기도 해서... 

> 😅 HSQLDB로 사용하도록 하자!
>
> * URL Mode
>
>   ```
>   jdbc:hsqldb:hsql://hsqldb-host:9001/spring-5-recipes-study-chap09
>   ```
>
> * Embedded Mode
>
>   ```
>   jdbc:hsqldb:mem:spring-5-recipes-study-chap09
>   ```
>
>   



### MySQL 8

log4jdbc를 사용할 때, HikariCP와 HSQLDB를 사용하면, HSQLDB에서 setNetworkTimeout() 기능을 지원하지 않아 log4jdbc에서 ERROR로그가 노출된다. 

그래서 MySQL을 사용해보기로 했다.

```sql
CREATE DATABASE spring_5_recipes_study_chap09 CHARACTER SET utf8mb4;

CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'springpass';
CREATE USER 'springuser'@'%' IDENTIFIED BY 'springpass';

GRANT ALL PRIVILEGES ON spring_5_recipes_study_chap09.* TO 'springuser'@'localhost';
GRANT ALL PRIVILEGES ON spring_5_recipes_study_chap09.* TO 'springuser'@'%';
-- 위와 같이하면 GRANT OPTION 빼고 모든 권한을 준다.
```

✔ MySQL을 사용하고나서 부터 ERROR 로그가 발생하지 않는 것을 확인했다.



## 진행간 이슈

* ...



## 정오표

* ...
