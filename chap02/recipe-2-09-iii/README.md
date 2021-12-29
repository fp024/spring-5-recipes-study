## 레시피 2-9-iii 후처리기를 만들어 POJO 검증 / 수정하기

* @Required로 프로퍼티 검사하기

* 스프링 5.1 부터 이 어노테이션이 Deprecated되어, 어노테이션 클래스는 남아있지만 동작하지 않음.

* 그리고, Java Config 설정 구성에서도 동작하지 않는 모습을 보임.

* XML 설정에 Spring 버전을 5.0.20.RELEASE 로 낮추었을 때, 

  ```groovy
  ext.springVersion = "5.0.20.RELEASE"
  ```

  ```
      04:10:57.657 [Test worker] INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loading XML bean definitions from class path resource [appContext.xml]   
      04:10:57.825 [Test worker] INFO  org.springframework.context.support.GenericXmlApplicationContext - Refreshing org.springframework.context.support.GenericXmlApplicationContext@b273a59: startup date [Thu Dec 30 04:10:57 KST 2021]; root of context hierarchy
      04:10:57.954 [Test worker] WARN  org.springframework.context.support.GenericXmlApplicationContext - Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sequenceGenerator' defined in class path resource [appContext.xml]: Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanInitializationException: Property 'suffix' is required for bean 'sequenceGenerator'  
  ```

  위의 오류 로그를 확인 할 수 있었음 (suffix에 @Required를 붙이고, 값설정을 안했을 때...)
