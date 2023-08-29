## 레시피 11-07 스텝 실행 제어하기



### 이번 예제는...

> 복잡하게 다 구현할 필요는 없이 하나의 Job이 여러 Step을 가질 수 있다는 정도로만 보여주면 될 것 같긴하다.



### 순차 스탭

순차 스탭에 대해서는

1. CSV 파일에서 회원정보를 DB에 넣는 부분 - 실제 구현함.

2. 지표를 계산해서 보고서 파일에 쓰기

   * DB를 읽는 부분 Spring Data JPA로 사용

     * 페이지 처리되도록 함.

       > ```java
       > List<UserRegistrationDTO> getOutstandingUserRegistrationBatchForDate(
       >       int page, int quantity, LocalDate registerDate);
       > ```
       >
       > 메서드 하나 만듬.

   * 지표 계산 관련해서는 그냥 콘솔 로그 출력 😅

3. 신규 등록 회원에 대해 성공한 건마다 메시지 큐 전송

   * 단순하게 Tasklet에서 콘솔 로그 출력 😅

     > 여기도 결국은 DB에서 회원 조회를 해야함.
     >
     > ✨✨✨ 스프링 배치에서 스텝간 데이터 공유는 권장하지 않음. 

