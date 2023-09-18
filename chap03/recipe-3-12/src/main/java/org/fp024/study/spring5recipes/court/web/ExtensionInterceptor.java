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
      ModelAndView modelAndView) {
    LOGGER.info("### ExtensionInterceptor - start");
    LOGGER.info("### accept: {}", request.getHeader("Accept"));

    String reportName = null;
    String reportDate = request.getQueryString().replace("date=", "").replace("-", "_");

    if (request.getServletPath().endsWith(".pdf")) {
      reportName = "ReservationSummary_" + reportDate + ".pdf";
    }
    if (request.getServletPath().endsWith(".xlsx")) {
      reportName = "ReservationSummary_" + reportDate + ".xlsx";
    }

    if (reportName != null) {
      response.setHeader("Content-Disposition", "attachment; filename=" + reportName);
    }

    LOGGER.info("### response header: {}", response.getHeader("Content-Disposition"));
  }
}
