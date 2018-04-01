package com.alibaba.dubbo.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;
import com.xianglesong.dubbo.dao.ElastiSearchDao;

@Service
public class DemoServiceImpl implements DemoService {

  @Autowired
  ElastiSearchDao elastiSearchDao;

  public String sayHello(String name) {
    System.out.println("say hello from spring boot elasticsearch: " + name);
    elastiSearchDao.indexExist(name);
    return "Hello " + name;
  }
}
