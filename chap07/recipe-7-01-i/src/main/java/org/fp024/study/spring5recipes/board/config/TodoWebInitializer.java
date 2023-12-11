package org.fp024.study.spring5recipes.board.config;

import static org.fp024.study.spring5recipes.board.common.Constants.PROJECT_ENCODING_VALUE;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TodoWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[0];
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {TodoWebConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }

  @Override
  protected Filter[] getServletFilters() {
    // Form 제출을 할 때, _method Hidden 폼으로 GET, POST 이외의 요청을 하기 위해서 추가 필터 설정.
    // Ajax 요청만을 쓰면 이 필터가 필요가 없을 텐데, HTML Form은 GET/POST만 지원해서 이런 필터를 사용한 것 같다.
    return new Filter[] {
      // HiddenHttpMethodFilter가 한글 깨짐을 발생시켜, CharacterEncodingFilter를 앞에다 두었다.
      new CharacterEncodingFilter(PROJECT_ENCODING_VALUE, true, true), //
      new HiddenHttpMethodFilter()
    };
  }
}
