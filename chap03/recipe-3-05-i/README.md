## 레시피 3-5-i 로케일별 텍스트 메시지 외부화하기



## 진행

* 기본을 한국어 메시지로 출력하기로 했다.

* http://localhost:8080/welcome?language=en 와 같이 영어로 언어를 선택했을 때 엉어로된 메시지 프로퍼티를 사용하게 했다.

* MessageSource 빈에 기본 인코딩을 설정했다.

  ```java
    @Bean
    public MessageSource messageSource() {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename("messages");
      messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name()); // 기본 인코딩 UTF-8
      return messageSource;
    }
  ```

* JSP View의 메시지 리소스 값을 확인하고 싶었는데, `@ResponseBody`로 반환한 내용이 아니라면 그냥 Mock 테스트에서는 View의 Body가 비어있는 것 같다.

  ```
  MockHttpServletResponse:
             Status = 200
      Error message = null
            Headers = [Set-Cookie:"language=en; Path=/; Max-Age=3600; Expires=Mon, 09 Jan 2023 15:26:12 GMT", Content-Language:"en"]
       Content type = null
               Body = 
      Forwarded URL = reservationQuery
     Redirected URL = null
            Cookies = [[Cookie@783115d9 name = 'language', value = 'en', comment = [null], domain = [null], maxAge = 3600, path = '/', secure = false, version = 0, httpOnly = false]]
  ```

  위에 Body 부분을 보면 그냥 빈문자열("") 인 것 같다.



## 기타

### Prettier의 JSP 자동 포멧팅이 좀 이상하다.

테그 선언부 부분의 줄바꿈을 좀 이상하게함.

Settings.json에서  html에 대해 자동 포맷팅은 제외하도록 하자.

```json
"[html]": {    
    "editor.formatOnSave": false,
    "editor.defaultFormatter": "esbenp.prettier-vscode",    
  },
```

