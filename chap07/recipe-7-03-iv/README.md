## 레시피 7-03-iv 유저 인증하기 - 패스워드 암호화

> 유저 인증 정보 저장소의 여려 종류를 알아본다.
>

### 이번 레시피에서 확인해야할  내용

* ✔ 인메모리 방식 

* ✔ DB 조회 방식 

* ✅ 패스워드 암호화 

* ⬛ LDAP 저장소 
  * 저자님 하신대로 도커 사용해야 편하겠다.

* ⬛ 유저 세부 캐시하기 
  * 이 내용은 자세하게 경험 해보질 않았는데.. 확실하게 하자.
    

## 진행

PasswordEncoder 설정

```java
  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
```

* Spring Security 5.8.8 환경에서는 패스워드 인코더를 따로 선언하지 않아도, 암호화 타입 접두어에 따라 자동 처리해주는 DelegatingPasswordEncoder를 만들어준다.
* 위의 빈 설정을 해주지 않아도 되긴함.
* Spring Security 5.8.8기준에서 만들어진 DelegatingPasswordEncoder의 기본 암호화는 bcyrpt임
  * https://github.com/spring-projects/spring-security/blob/e4e31f2c900fd85a5517b92e5238cea01ac9535d/crypto/src/main/java/org/springframework/security/crypto/factory/PasswordEncoderFactories.java#L80



Spring Shell을 설치한 상태면 다음 명령으로 평문에 대한 패스워드 암호화 값을 확인할 수 있다.

```
❯ spring encodepassword admin
{bcrypt}$2a$10$4k68vngvW27pKYnjZe8qG.xzAR515YxKULjgHabszjX1CrhKc2mEi
❯ spring encodepassword user00
{bcrypt}$2a$10$RRMggW9wP3d4pD34xnD5Oe3.WitfSUJQ2xYt8V6dhXX6QOJnb3fmS
❯ spring encodepassword user01
{bcrypt}$2a$10$vL.Zffr5FCU5o4AJN.Z7heB3rEFNB6VoQKmGLqWEEhurhZJhdz9ny
❯
```

✨ 암호화 데이터가 암호화 타입 접두어 합쳐서 68자 정도 이므로 암호 저장 컬럼 크기가 100자 정도는 되야할 것 같다.




## 의견

* ...



---

## 기타

* ...

  

## 정오표

* ...
  


---

## JavaDocs

* ...
