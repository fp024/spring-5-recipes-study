## 레시피 11-03 커스텀 ItemWriter/ItemReader 작성하기

* ItemReader, ItemWriter의 사용자 정의 방법을 알려주긴 했는데, 완전한 코드를 예시로 제공하지 않았다.
* 구현이 없어서 UserRegistrationServiceImpl 클래스를 만들어 대략적으로 구현했다. User 컬랙션을 최초 5개를 반환하고 이후 반환하지 못하게 하여, Job 실행시 무한 루프는 막았다.
  * read() 가 null을 반환할 때 더이상 처리할 것이 없는 것으로 인식함.

