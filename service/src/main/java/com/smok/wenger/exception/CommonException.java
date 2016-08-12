package com.smok.wenger.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MultipartException;

import java.util.Locale;

/**
 * Created by smok.laf on 2016/8/12 11:52
 */
public class CommonException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private static MessageSource messageSource;

  static {
    messageSource = new ClassPathXmlApplicationContext("spring/errmsg-context.xml");
  }

  private String section;
  private String errorId;

  public String getSection() {
    return section;
  }

  public String getErrorId() {
    return errorId;
  }

  public String getFullErrorId() {
    return section + "." + errorId;
  }

  private static String formatErrorMessage(final String errorId, Object... args) {
    String msg = messageSource.getMessage(errorId, args, Locale.CHINA);
    if (msg == null) {
      return "Error: Invalid error message Id " + errorId;
    }
    return String.format(msg, args);
  }

  public CommonException(String errorMessage) {
    super(errorMessage);
  }

  public CommonException(Throwable cause, String section, String errorId, Object... detail) {
    super(formatErrorMessage(section + "." + errorId, detail), cause);
    this.section = section;
    this.errorId = errorId;
  }

  public static CommonException wrapIfNeeded(Throwable exception, String details) {
    if (exception instanceof CommonException) {
      return (CommonException) exception;
    } else if (exception instanceof BindException || exception instanceof TypeMismatchException
        || exception instanceof MissingServletRequestParameterException) {
      return INVALID_PARAMETER(exception, "", details);
    } else if (exception instanceof IllegalArgumentException) {
      return INVALID_PARAMETER(exception, "", details);
    } else if (exception instanceof MultipartException) {
      return NOT_ALLOWED("session timeout,so 302 and login again!");
    } else if (exception instanceof HttpMediaTypeNotAcceptableException) {
      return NOT_ALLOWED("http upload post 406 error!");
    } else if (exception instanceof HttpRequestMethodNotSupportedException) {
      return NOT_ALLOWED("请求方法头不合法，请刷新后重试，給您带来的不便请原谅");
    } else {
      return INTERNAL(exception, details);
    }
  }

  public static CommonException INTERNAL(Throwable ex, String detail) {
    return new CommonException(ex, "HC-CRM", "INTERNAL_ERROR", detail);
  }

  /**
   * This exception is designed to be thrown only when initializing wolong web application. When
   * this exception is thrown, the web container will catch it and abort the application deployment,
   * which is just what we want. Be sure that all code that can throw this exception will be called
   * during web application initialization.
   */
  public static CommonException APP_INIT_ERROR(Throwable ex, String detail) {
    return new CommonException(ex, "WENGER", "APP_INIT_ERROR", detail);
  }

  public static CommonException NOT_FOUND(Throwable ex, String object, String objectName) {
    return new CommonException(ex, "WENGER", "NOT_FOUND", object, objectName);
  }

  public static CommonException NOT_FOUND(String object, String objectName) {
    return NOT_FOUND(null, object, objectName);
  }

  public static CommonException ALREADY_EXISTS(Throwable ex, String object, String objectName) {
    return new CommonException(ex, "WENGER", "ALREADY_EXISTS", object, objectName);
  }

  public static CommonException ALREADY_EXISTS(String object, String objectName) {
    return ALREADY_EXISTS(null, object, objectName);
  }

  public static CommonException INVALID_PARAMETER(Throwable ex, String field, Object value) {
    return new CommonException(ex, "WENGER", "INVALID_PARAMETER", field, value);
  }

  public static CommonException INVALID_PARAMETER_WITHOUT_EQUALS_SIGN(Throwable ex, String field,
      Object value) {
    return new CommonException(ex, "WENGER", "INVALID_PARAMETER_WITHOUT_EQUALS_SIGN", field, value);
  }

  public static CommonException INVALID_PARAMETER(String field, Object value) {
    return INVALID_PARAMETER(null, field, value);
  }

  public static CommonException INVALID_PARAMETER_WITHOUT_EQUALS_SIGN(String field, Object value) {
    return INVALID_PARAMETER_WITHOUT_EQUALS_SIGN(null, field, value);
  }

  public static CommonException BAD_REST_CALL(Throwable ex, String reason) {
    return new CommonException(ex, "WENGER", "BAD_REST_CALL", reason);
  }

  public static CommonException NOT_ALLOWED(String reason) {
    return new CommonException(null, "WENGER", "NOT_ALLOWED", reason);
  }
}
