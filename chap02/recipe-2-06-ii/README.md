## 레시피 2-6-ii 외부 리소스(텍스트, XML, 프로퍼티, 이미지 파일)의 데이터 사용하기

* 어노테이션, XML 설정 방식 테스트를 인자를 전달해서 확인하도록 코드를 구성했다. 아래처럼 입력하면 XML 방식 컨텍스트를 사용한다.
  * XML
    * `gradle clean run --args='XML'`
  * ANNOTATION
    * `gradle clean run`, `gradle clean run --args='XML'`