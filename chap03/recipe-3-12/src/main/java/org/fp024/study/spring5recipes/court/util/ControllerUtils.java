package org.fp024.study.spring5recipes.court.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.WebUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerUtils {

  /*
    폼에서는 전달받은 파라미터 이름 _target0, _target1, _target2 를 구분해서 끝의 index 번호를 가져오는데.
    그냥 `String`을 치환해서 가져오지 않고, 뭔가 처리가 들어간 것 같다.

    이미지 버튼일 경우, 전달 받은 파라미터가 .x, .y로 끝나는 형식으로 올 수 있나보다.
  */
  public static int getTargetPage(HttpServletRequest request, String paramPrefix, int currentPage) {
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = paramNames.nextElement();
      if (paramName.startsWith(paramPrefix)) {
        for (int i = 0; i < WebUtils.SUBMIT_IMAGE_SUFFIXES.length; i++) {
          String suffix = WebUtils.SUBMIT_IMAGE_SUFFIXES[i];
          if (paramName.endsWith(suffix)) {
            paramName = paramName.substring(0, paramName.length() - suffix.length());
          }
        }
        return Integer.parseInt(paramName.substring(paramPrefix.length()));
      }
    }
    return currentPage;
  }
}
