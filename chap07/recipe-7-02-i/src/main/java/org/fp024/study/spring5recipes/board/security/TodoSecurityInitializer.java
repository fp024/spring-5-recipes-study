package org.fp024.study.spring5recipes.board.security;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

public class TodoSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
  @Override
  protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    // Form 제출을 할 때, _method Hidden 폼으로 GET, POST 이외의 요청을 하기 위해서 추가 필터 설정.
    // Ajax 요청만을 쓰면 이 필터가 필요가 없을 텐데, HTML Form은 GET/POST만 지원해서 이런 필터를 사용한 것 같다.
    // ✨ 현재 이 프로젝트가 PUT이나, DELETE 요청을 form의 POST로 보낸뒤 필터에서 _method Hidden 값을 보고 판별하므로
    //    스프링 시큐리티의 필터보다 앞에 위치해야한다.
    FilterRegistration.Dynamic encodingFilter =
        servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter());

    encodingFilter.addMappingForUrlPatterns(
        null, false, "/*"); // 첫번째 인자 dispatcherTypes를 null로 두면 REQUEST로 인식 한다고 함.
  }
}
