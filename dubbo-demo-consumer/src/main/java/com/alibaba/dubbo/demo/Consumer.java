package com.alibaba.dubbo.demo;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
  public static void main(String[] args) throws Exception {
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext(new String[] {"META-INF/spring/dubbo-demo-consumer.xml"});
    context.start();
    // obtain proxy object for remote invocation
    DemoService demoService = (DemoService) context.getBean("demoService");
    // execute remote invocation
    String hello = demoService.sayHello("world");
    // show the result
    System.out.println(hello);
    
//    hello = demoService.sayHello("world2");
//    // show the result
//    System.out.println(hello);
//    
//    hello = demoService.sayHello("world3");
//    // show the result
//    System.out.println(hello);
//    
//    hello = demoService.sayHello("world4");
//    // show the result
//    System.out.println(hello);
  }
}
