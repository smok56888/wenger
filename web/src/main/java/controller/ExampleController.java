package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring-mvc 接收参数、返回类型示例
 * Created by smok.laf on 2016/8/1 17:50
 */
@Controller
@RequestMapping("/example/*")
public class ExampleController {

  @RequestMapping("/normal")
  @ResponseBody
  public Object normalReturn() {
    return null;
  }
}