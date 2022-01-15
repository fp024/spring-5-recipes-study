# 스프링 5 레시피 스터디

이 책을 개인적으로도 사고, 회사에서도 사줘서 2권이나 있는데, 제대로 안보았다. 괜찮은 책일 것 같은데...😌

Gradle 멀티 프로젝트 구성에서 예제를 하나씩 추가해가며 실습해보자!



### 저자

마틴 데니엄, 다니엘 루비오, 조시 롱

### 역자

이일웅 님

### 도서 판매처

#### Yes24

* http://www.yes24.com/Product/Goods/63713129

#### 교보문고

* http://www.kyobobook.co.kr/product/detailViewKor.laf?barcode=9791162241035





## 목차

### Chapter 1 [스프링 개발 툴](chap01)

### Chapter 2 [스프링 코어](chap02)

### Chapter 3 [스프링 MVC](chap03) 

### Chapter 4 [스프링 REST](chap04)

### Chapter 5 [스프링 MVC : 비동기 처리](chap05)

### Chapter 6 [스프링 소셜](chap06) 

### Chapter 7 [스프링 시큐리티](chap07) 

### Chapter 8 [스프링 모바일](chap08) 

### Chapter 9 [데이터 액세스](chap09) 

### Chapter 10 [스프링 트랜젝션 관리](chap10) 

### Chapter 11 [스프링 배치](chap11) 

### Chapter 12 [스프링 NoSQL](chap12)

### Chapter 13 [스프링 자바 엔터프라이즈 서비스와 원격기술](chap13) 

### Chapter 14 [스프링 메시징](chap14) 

### Chapter 15 [스프링 인티그레이션](chap15) 

### Chapter 16 [스프링 테스트](chap16) 

### Chapter 17 [그레일즈](chap17) 





## 후기





## 기타

* Visual Studio Code에 google-java-format을 연동해서 사용하는데, `Shift + Alt + f` 할 때, Import 정리까지 같이해주니 일부러  `Shift + Alt + o` 를 따로 눌러줄 필요는 없다. Import 정리는 또 포메팅 형식이 달라서 혼선이 올 수 있음. 

* 모든장의 root를 하나로 하려다보니 시간이 너무걸려서 장 하나마다 settings.gradle을 만들어주는 배치파일을 만들었다.  프로젝트 루트의 gradle.properties를 수정하거나, 최초 프로젝트 클론을 했을 때, 아래 배치 파일을 수행해주도록 하자!
  * [make-gradle-properties.bat](make-gradle-properties.bat)
  * [clean-gradle-properties.bat](clean-gradle-properties.bat)

