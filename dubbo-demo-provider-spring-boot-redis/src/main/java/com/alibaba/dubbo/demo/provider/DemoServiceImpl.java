package com.alibaba.dubbo.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;
import com.xianglesong.dubbo.dao.RedisDao;
import com.xianglesong.dubbo.entity.User;

@Service
public class DemoServiceImpl implements DemoService {

  @Autowired
  RedisDao redisDao;

  public String sayHello(String name) {
    System.out.println("say hello from spring boot redis: " + name);

    redisDao.execute();

    return "Hello " + name;
  }
}
