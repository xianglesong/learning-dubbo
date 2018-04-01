package com.alibaba.dubbo.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;
import com.xianglesong.dubbo.dao.MongoDao;

@Service
public class DemoServiceImpl implements DemoService {

  @Autowired
  MongoDao mongoDao;

  public String sayHello(String name) {
    System.out.println("say hello from spring boot mongo: " + name);

    mongoDao.execute();

    return "Hello " + name;
  }
}
