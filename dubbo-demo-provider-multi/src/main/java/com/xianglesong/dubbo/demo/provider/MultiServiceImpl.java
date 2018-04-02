package com.xianglesong.dubbo.demo.provider;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;
import com.xianglesong.dubbo.demo.MultiService;

@Service
// @Component
public class MultiServiceImpl implements MultiService {

  @Autowired
  DemoService demoService;

  public String mutliExecute(String name) {
    System.out.println("multi execute: " + name);
    String result = "";
    result = demoService.sayHello(name);
    System.out.println("result: " + result);
    return result + " in multi service.";
  }

}
