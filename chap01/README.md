# Chapter 1 스프링 개발툴



STS, IntelliJ, Maven / Gradle Wrapper 등으로 예제 프로젝트 실행 방법을 알려주고 있다.

기본으로는 IntelliJ + Gradle 프로젝트로 예제를 진행하고, 

전체 프로젝트를 한번에 읽으면 보기가 힘든 부분이 있어서,

레시피가 완료될 때마다, 루트의 settings.gradle에 프로젝트를 추가하는 것이 나을 것 같다.

특정 레시피만 포함하더라도, 루트의 settings.gradle을 찾아 전체 레시피를 전부 읽어들여서, 이렇게 해야겠다.





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





## 정오표

* p23
  * CHAPTER 1 그레일즈 -> CHAPTER 17 그레일즈