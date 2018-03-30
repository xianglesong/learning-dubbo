package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.DemoService;

public class DemoServiceImpl implements DemoService {
  public String sayHello(String name) {
    System.out.println("say hello from two");
    return "Hello From Two: " + name;
  }
}
