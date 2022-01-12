## 레시피 2-21 스프링에서 AspectJ 애스펙트 구성하기

* 예제의 build.gradle을 보니 이 예제에서도 aspectj 플러그인 사용한 것 같다.

* 플러그인 사용하지 않고, javaagent에 aspectweaver를 추가하는 식으로 진행했는데,  시작시 아래와 같은 경고는 뜬다.

  ```
  WARNING: An illegal reflective access operation has occurred
  WARNING: Illegal reflective access using Lookup on org.aspectj.weaver.loadtime.ClassLoaderWeavingAdaptor (file:/C:/Users/사용자명/.gradle/caches/modules-2/files-2.1/org.aspectj/aspectjweaver/1.9.7/158f5c255cd3e4408e795b79f7c3fbae9b53b7ca/aspectjweaver-1.9.7.jar) to class java.lang.ClassLoader
  WARNING: Please consider reporting this to the maintainers of org.aspectj.weaver.loadtime.ClassLoaderWeavingAdaptor
  WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
  WARNING: All illegal access operations will be denied in a future release
  ```

  

* Aspects라는 팩토리 클래스를 활용하여 원하는 대로 구성하는 예시

  * 프레임워크가 자체적으로 인스턴스화하는 것 대신 구성.

  
