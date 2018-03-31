package com.xianglesong.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.demo.DemoService;

@SpringBootApplication
public class SpringConsumer {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SpringConsumer.class, args);
    System.out.println("begin");
    context.start();
    try {
      DemoService demoService = (DemoService) context.getBean("demoService");
      // execute remote invocation
      String hello = demoService.sayHello("1");
      // show the result
      System.out.println(hello);
      System.out.println("end");
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
