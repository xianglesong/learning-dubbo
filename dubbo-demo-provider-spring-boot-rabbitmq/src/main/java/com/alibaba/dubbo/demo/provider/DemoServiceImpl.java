package com.alibaba.dubbo.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

  public String sayHello(String name) {
    System.out.println("say hello from spring boot redis: " + name);

    return "Hello " + name;
  }
}
