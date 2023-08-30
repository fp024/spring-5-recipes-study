## 레시피 11-09 잡 실행하기 - 잡을 매개변수화하기



### 이번 예제는...

> 파라미터를 스텝에 어떻게 전달할 수 있는지 보는 것이므로, 스케쥴러 관련 코드는 제거하는게 낫겠다.
>
> 저자님 예제를 보면 스케쥴러 코드를 그대로 유지하면서 main메서드로 Job실행하는 코드가 혼재되어있다.
>
> * 스케쥴러 코드는 지우고, 메인 메서드에서 스텝으로 JobParameter 전달이 잘되는지 보자!



### `@StepScope` 가 적용되는 범위는...

Step의 구성요소인 reader나 writer등에 선언해야 제대로 됨. Step 메서드 위에다 선언하면 에러남..

```
Caused by: java.lang.IllegalStateException: No context holder available for step scope
```

Step 메서드 위에는 `@JobScope` 를 붙여야 했음.



### 진행

csv파일을 읽고 쓰는 세부 구현을 하지않고 단지 파라미터가 잘 전달되는지만 확인 되도록 구현했다.

@StepScope를 추가해서 Job 파라미터가 구현되는 것을 확인하려면 reader나 writer를 반환하는 메서드위에 붙여야되서..

단순하게 파라미터로 max.count 값을 받아서 청크단위 5씩 읽는 reader와 그것을 단순 화면 출력하는 writer를 만들었다.

```java
@StepScope
  @Bean
  ItemReader<Long> customReader(@Value("#{jobParameters['max.count']}") Long maxCount) {
    return new ItemReader<>() {
      private Long count = 0L;
      
      @Override
      public Long read() throws Exception {
        return fetchData();
      }

      private Long fetchData() {
        if (maxCount > count) {
          return ++count;
        } else {
          return null;
        }
      }
    };
  }

  @Bean
  ItemWriter<Long> customWriter() {
    return items -> LOGGER.info("👺 그냥 화면 출력: {}", items);
  }
```



---

### 이걸로... 11장 스프링 배치는 다 봤는데..

중간에 도무지 이해가 안되서 잠깐 멈췄었는데... 그사이에 스프링 배치 완벽가이드 2판을 5장까지 볼 기회가 있었는데...

그것을 보고나니 나머지 부분들이 이해가 잘되었다. 👍

그리고 빙(Bing)도 Reader 클래스를 구현하는 방법에 대해서 잘 알려줘서 큰 도움이 되었음 😉👍

* 책 추천: 
  * 스프링 배치 완벽 가이드 2/e
    *  https://www.yes24.com/Product/Goods/99422216

