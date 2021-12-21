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

### Chapter 2 스프링 코어

### Chapter 3 스프링 MVC 

### Chapter 4 스프링 REST

### Chapter 5 스프링 MVC : 비동기 처리

### Chapter 6 스프링 소셜 

### Chapter 7 스프링 시큐리티 

### Chapter 8 스프링 모바일 

### Chapter 9 데이터 액세스 

### Chapter 10 스프링 트랜젝션 관리 

### Chapter 11 스프링 배치 

### Chapter 12 스프링 NoSQL

### Chapter 13 스프링 자바 엔터프라이즈 서비스와 원격기술 

### Chapter 14 스프링 메시징 

### Chapter 15 스프링 인티그레이션 

### Chapter 16 스프링 테스트 

### Chapter 17 그레일즈 









## 진행 간 이슈

### Task 'prepareKotlinBuildScriptModel' not found in project ':app' 오류

Gradle을 멀티 프로젝트로 구성 후 하위 프로젝트만 IntelliJ 에서 읽으려할 때, 빌드오류가 난다. 회피 방법으로 이렇게 prepareKotlinBuildScriptModel task를 만들고 내용을 비워두거나,
```groovy
task prepareKotlinBuildScriptModel {
 // 비어있는 내용으로 task 만들어두기
}
```

코틀린 플러그인을 비활성화하라고 했다.
*  `Preferences` > `Plugin` > `Disable Kotlin`
