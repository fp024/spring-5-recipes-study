## 레시피 2-17-ii AspectJ 포인트컷 표현식 작성하기

* 2-17-ii 예제도 이상한데.. @LoggingRequired 를 대상 클래스 단위에 선언하고, 포인트 컷은 아래와 같이 했다.
  `@Pointcut("within(@LoggingRequired *)")`