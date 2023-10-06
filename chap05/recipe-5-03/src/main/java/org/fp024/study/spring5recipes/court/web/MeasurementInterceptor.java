package org.fp024.study.spring5recipes.court.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class MeasurementInterceptor implements AsyncHandlerInterceptor {
  public static final String START_TIME = "startTime";

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    LOGGER.info("### preHandle ###");
    if (request.getAttribute(START_TIME) == null) {
      request.setAttribute(START_TIME, System.currentTimeMillis());
    }
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {

    LOGGER.info("### postHandle ###");
    long startTime = (Long) request.getAttribute(START_TIME);
    request.removeAttribute(START_TIME);
    long endTime = System.currentTimeMillis();
    long responseProcessingTime = endTime - startTime;

    System.out.println("[postHandle] Response-Processing-Time: " + responseProcessingTime + "ms.");
    System.out.println(
        "[postHandle] Response-Processing-Thread: " + Thread.currentThread().getName());

    // ✨ 비동기 REST 호출시는 ModelAndView를 생성하지 않아 null 임
    if (modelAndView != null) {
      modelAndView.addObject("handlingTime", responseProcessingTime);
    }
  }

  @Override
  public void afterConcurrentHandlingStarted(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    long startTime = (Long) request.getAttribute(START_TIME);
    request.setAttribute(START_TIME, System.currentTimeMillis());
    long endTime = System.currentTimeMillis();

    System.out.println(
        "[afterConcurrentHandlingStarted] Request-Processing-Time: "
            + (endTime - startTime)
            + "ms.");
    System.out.println(
        "[afterConcurrentHandlingStarted] Request-Processing-Thread: "
            + Thread.currentThread().getName());
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex) {
    LOGGER.info("### afterCompletion ###");
  }
}
