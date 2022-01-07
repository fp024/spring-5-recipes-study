## 레시피 2-12 POJO에게 IoC 컨테이너 리소스 알려주기

* 빈이 IoC 컨테이너 리소스를 인지하게 하려면 Aware(인지) 인터페이스를 구현해야함.

* 자주 쓰는 스프링 인터페이스

  | Aware 인터페이스             | 대상 리소스 타입                                             |
  | ---------------------------- | ------------------------------------------------------------ |
  | BeanNameAware                | IoC 컨테이너에 구성한 인스턴스의 빈이름                      |
  | BeanFactoryAware             | 현재 빈 팩토리, 컨테이너 서비스를 호출하는데 쓰임            |
  | ApplicationContenxtAware     | 현재 애플리케이션 컨택스트, 컨테이너 서비스를 호출하는데 쓰임 |
  | MessageSourceAware           | 메시지 소스, 텍스트 메시지를 해석하는 데 쓰임                |
  | ApplicationEventPublishAware | 애플리케이션 이벤트 발행기(퍼블리셔), 애플리케이션 이벤트를 발행하는데 쓰임 |
  | ResourceLoaderAware          | 리소스 로더, 외부 리소스를 로드하는데 쓰임                   |
  | EnvironmentAware             | ApplicationContext 인터페이스에 묶인 org.springframework.core.env.Environment 인스턴스 |

  
  
  

### 멀티 프로젝트 구성에서는, 이전 build.gradle의 설정을 수행하는 것 같다.

```bash
C:\git\spring-5-recipes-study\chap02\recipe-2-12>gradle check

> Configure project :chap02:recipe-2-11
activeProfiles is null or empty: null          # 이전 프로젝트의 로그가 로그가 남는다. 검증을 위해 호출을 한번 해보는건가?
                                               # 적당히 봐서 logger 수준을 info로 바꿔도 될 것 같다.
> Configure project :chap02:recipe-2-12
activeProfiles is null or empty: null

> Task :chap02:recipe-2-12:test

MainTest > testMain() STANDARD_OUT
    09:45:00.589 [Test worker] INFO  org.fp024.study.spring5recipes.shop.MainTest - fileName: cashier.txt
    09:45:00.656 [Test worker] INFO  org.fp024.study.spring5recipes.shop.Main - active profiles: [global, winter]
    Shopping cart 1 contains [Product(name=AAA, price=2.0), Product(name=CD-RW, price=1.0)]
    Shopping cart 2 contains [Product(name=DVD-RW, price=2.5)]
    09:45:00.855 [Test worker] INFO  org.fp024.study.spring5recipes.shop.MainTest -     2022-01-07 09:45:00     [Product(name=AAA, price=2.0), Product(name=CD-RW, price=1.0)]
    09:45:00.855 [Test worker] INFO  org.fp024.study.spring5recipes.shop.MainTest -     2022-01-07 09:45:00     [Product(name=DVD-RW, price=2.5)]
    09:45:00.855 [Test worker] INFO  org.fp024.study.spring5recipes.shop.MainTest - file deleted...

BUILD SUCCESSFUL in 2s
6 actionable tasks: 3 executed, 3 up-to-date
C:\git\spring-5-recipes-study\chap02\recipe-2-12>
```

