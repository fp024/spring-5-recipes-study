package org.fp024.study.spring5recipes.court.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ExtensionInterceptor implements HandlerInterceptor {

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    LOGGER.info("### ExtensionInterceptor - start");
    String reportName = null;
    String reportDate = request.getQueryString().replace("date=", "").replace("-", "_");
    // 현재 프로젝트에 PDF 또는 엑셀 변환 기능을 적용하지 않았으니, html로 요청이 왔을 때, HTML파일을 다운로드 받게 설정
    if (request.getServletPath().endsWith(".html")) {
      reportName = "ReservationSummary_" + reportDate + ".html";
    }
    // if (request.getServletPath().endsWith(".pdf")) {
    //   reportName = "ReservationSummary_" + reportDate + ".pdf";
    // }
    // if (request.getServletPath().endsWith(".xls")) {
    //   reportName = "ReservationSummary_" + reportDate + ".xls";
    // }

    if (reportName != null) {
      response.setHeader("Content-Disposition", "attachment; filename=" + reportName);
    }

    LOGGER.info("### response header: {}", response.getHeader("Content-Disposition"));
  }
}
