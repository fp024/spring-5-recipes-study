## 레시피 11-04 출력하기 전에 입력 데이터 처리하기

* Reader읽은 데이터를 가공하여(ItemProcessor사용) 출력
* DB에 출력(Write) 하기전에 유효성 검증을 수행.
* 검증 통과한 행만 DB에 입력됨.



### BATCH_STEP_EXECUTION

배치 작업을 한번 수행한후 `BATCH_STEP_EXECUTION` 테이블 내용 확인

FILTER_COUNT를 보니 일부러 3개 유효성 검사 실패되게 만든 것이 나타난다.

| STEP\_EXECUTION\_ID | VERSION | STEP\_NAME                       | JOB\_EXECUTION\_ID | START\_TIME                | END\_TIME                  | STATUS    | COMMIT\_COUNT | READ\_COUNT | FILTER\_COUNT | WRITE\_COUNT | READ\_SKIP\_COUNT | WRITE\_SKIP\_COUNT | PROCESS\_SKIP\_COUNT | ROLLBACK\_COUNT | EXIT\_CODE | EXIT\_MESSAGE | LAST\_UPDATED              |
| :------------------ | :------ | :------------------------------- | :----------------- | :------------------------- | :------------------------- | :-------- | :------------ | :---------- | :------------ | :----------- | :---------------- | :----------------- | :------------------- | :-------------- | :--------- | :------------ | :------------------------- |
| 0                   | 5       | User Registration CSV To DB Step | 0                  | 2022-01-31 02:22:28.050000 | 2022-01-31 02:22:28.121000 | COMPLETED | 3             | 10          | 3             | 7            | 0                 | 0                  | 0                    | 0               | COMPLETED  |               | 2022-01-31 02:22:28.123000 |



### 처리기 연결

* 현재 예제에는 없는데, CompositeItemProcessor 를 나중에라도 구현해서 해보자! 여러개의 ItemProcessor를 순서대로 묶을 수 있다. (하나의 선형 흐름..)
