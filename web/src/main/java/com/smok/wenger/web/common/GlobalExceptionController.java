package com.smok.wenger.web.common;

import com.smok.wenger.exception.CommonException;
import com.smok.wenger.vo.WebResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by smok.laf on 2016/8/12 11:46
 */
@ControllerAdvice
public class GlobalExceptionController {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  protected WebResponse handleException(Exception ex, HttpServletRequest req) {
    String url = req.getRequestURI();
    if (StringUtils.isNotBlank(req.getQueryString())) {
      url += "?" + req.getQueryString();
    }

    if (ex instanceof CommonException) {
      CommonException commonException = CommonException.wrapIfNeeded(ex, ex.getMessage());
      // TODO 自定义异常捕获&处理逻辑
      return WebResponse.error(commonException.getMessage());
    }

    // TODO 非捕获异常处理逻辑 报警？！
    return WebResponse.error("内部错误，请稍后再试，给您带来的不便请原谅。");
  }
}
