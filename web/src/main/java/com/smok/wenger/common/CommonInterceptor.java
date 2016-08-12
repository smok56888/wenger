package com.smok.wenger.common;

import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by smok.laf on 2016/8/12 14:47
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

  private NamedThreadLocal<Long> startTimeThreadLocal =
      new NamedThreadLocal<Long>("RequestWatch-StartTime");

  public boolean preHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o) throws Exception {
    startTimeThreadLocal.set(System.currentTimeMillis());
    // TODO 这里可以做检查登录操作
    return true;
  }

  public void postHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
      throws Exception {

  }

  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    long endTime = System.currentTimeMillis();
    long startTime = startTimeThreadLocal.get();
    String requestTimeInfo = String
        .format("URL:%s cost: %d ms", httpServletRequest.getRequestURI(), endTime - startTime);
    System.out.println(requestTimeInfo);
  }
}
