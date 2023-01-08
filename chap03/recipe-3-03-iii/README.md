## 레시피 3-3-iii 핸들러 인터셉터로 요청 가로채기 3



## 진행

* 핸들러 인터셉터를 원하는 경로에만 실행되도록 지정

> ExtensionInterceptor의 역활이 파일 확장자에 따라 파일을 다운로드 해주는 기능인데...
>
> 뷰의 내용을 파일로 다운로드가 되도록 헤더를 추가해주는 역할은 있지만, 그외 것들이 없는 상태였다.
>
> 일단은 date 파라미터를 전달하면 해당 날자의 예약정보만 html 파일로 다운로드되도록 코드를 수정했다.
>
> 해당 인터셉터가 응답헤더에 `Content-Disposition` 의 값을 정상적으로 설정하는지도 확인했다.

* 테스트 URL
  * http://localhost:8080/reservationSummary.html?date=2023-01-01 // html 파일을 다운로드 함.
  * http://localhost:8080/reservationSummary.pdf?date=2023-01-01 // ExtensionInterceptor 를 거쳐가긴 하지만, PDF처리 코드를 넣진 않아서 그냥 브라우저에서 화면 출력만한다.

