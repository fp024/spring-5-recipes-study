## 레시피 16-01-ii JUnit과 TestNG로 단위 테스트 작성하기

> 16장 스프링 테스트 시작

### 이번 레시피에서 확인해야할  내용

* ✔ **16-01-i**: JUnit 4 테스트 코드 작성

* ✅ **16-01-ii**: TestNG 테스트 코드 작성

* ⬜ ...



## 진행

TestNG 테스트 코드를 작성하여 실행하는 예제이다.

저자님은 독자가 프로젝트를 완전히 만들어보길 기대하기 때문에... 😅 클래스만 있어서, 

Gradle 프로젝트를 직접 만드신 역자님의 소스를 참조하게 되는데..

만드신 build.gradle이 좀 복잡하다.. 😂 (Gradle 4.4 환경)

* https://github.com/nililee/spring-5-recipes/blob/master/spring-recipes-4th/ch16/recipe_16_1_ii/build.gradle

현시점의 나의 Gradle 8.8 환경에서는 그냥 단순하게 TestNG 디펜던시하고 useJUnitPlatform() 대신에 useTestNG()를 사용해주면 되었다.

```groovy
dependencies {
  testImplementation (libs.testng)
}

tasks.named('test') {
  useTestNG()
}
```

vscode-java-test 확장으로 실행하면 다음과 같이 정돈된 느낌으로 디버깅 콘솔이 출력됨. 👍

```
23:08:13.033 [main] INFO  org.testng.internal.Utils - [TestNG] Running:
  Command line suite

23:08:13.143 [main] INFO  org.fp024.study.spring5recipes.bank.SimpleInterestCalculatorTestNGTests - ### calculate() ###
23:08:13.149 [main] INFO  org.fp024.study.spring5recipes.bank.SimpleInterestCalculatorTestNGTests - ### illegalCalculate()() ###

===============================================
Default Suite
Total tests run: 2, Passes: 2, Failures: 0, Skips: 0
===============================================
```








## 의견

* Gradle 최신 버전 사용시 JUnit 사용할 때는 다음 디펜던시를 추가하지 않으면 경고가 나타났는데, 

  ```groovy
  testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
  ```

  TestNG는 따로 추가하라는 경고는 나타나지 않았다.

  * https://docs.gradle.org/8.3/userguide/upgrading_version_8.html#test_framework_implementation_dependencies



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
* ...

