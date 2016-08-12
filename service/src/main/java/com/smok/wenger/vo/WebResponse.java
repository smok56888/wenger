package com.smok.wenger.vo;

/**
 * 通用web返回对象
 * Created by smok.laf on 2016/8/11 17:15
 */
public class WebResponse {

  public static final int SUCCESS_STATUS = 0;
  public static final int ERROR_STATUS = 1;
  private int status;
  private String message;
  private Object data;
  private Object extend;

  public WebResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public WebResponse(int status, String message, Object data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public WebResponse(int status, String message, Object data, Object extend) {
    this.status = status;
    this.message = message;
    this.data = data;
    this.extend = extend;
  }

  public static WebResponse success() {
    return new WebResponse(SUCCESS_STATUS, "success");
  }

  public static WebResponse success(String message) {
    return new WebResponse(SUCCESS_STATUS, message);
  }

  public static WebResponse success(Object data) {
    return new WebResponse(SUCCESS_STATUS, "success", data);
  }

  public static WebResponse success(Object date, Object extend) {
    return new WebResponse(SUCCESS_STATUS, "success", date, extend);
  }

  public static WebResponse error() {
    return new WebResponse(ERROR_STATUS, "error");
  }

  public static WebResponse error(String message) {
    return new WebResponse(ERROR_STATUS, message);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Object getExtend() {
    return extend;
  }

  public void setExtend(Object extend) {
    this.extend = extend;
  }
}
