package com.xianglesong.dubbo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.demo.DemoService;

import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {
  public static Logger logger = LoggerFactory.getLogger(TestController.class);

  @Autowired
  DemoService demoService;

  @PostConstruct
  public void init() {
    logger.info("init");
  }

  @PreDestroy
  public void destroy() {
    logger.info("destroy");
  }

  // http://localhost:8099/test?name=1
  @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
  public String exec(HttpServletRequest request, HttpServletResponse response)
      throws ParseException {
    logger.info("exec");
    String name = request.getParameter("name");
    String hello = demoService.sayHello(name);
    // show the result
    System.out.println(hello);
    System.out.println("end");
    return hello;
  }

  // http://localhost:8099/testuser?name=1
  @RequestMapping(value = "/testuser", method = {RequestMethod.GET, RequestMethod.POST})
  public String execUser(HttpServletRequest request, HttpServletResponse response)
      throws ParseException {
    logger.info("exec");
    String name = request.getParameter("name");

    // show the result
    System.out.println();
    System.out.println("end");
    return "result";
  }
}
