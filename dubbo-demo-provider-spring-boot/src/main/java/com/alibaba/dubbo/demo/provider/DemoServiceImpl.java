package com.alibaba.dubbo.demo.provider;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.demo.DemoService;
import com.xianglesong.dubbo.entity.User;
import com.xianglesong.dubbo.mapper.UserMapper;

@Service
public class DemoServiceImpl implements DemoService {
  
  @Resource
  private UserMapper userMapper;

  public String sayHello(String name) {
    System.out.println("say hello from spring boot: " + name);

    User user = new User();
    user.setId(1);
    user.setIdentity(2);
    user.setSex(3);
    user.setCreatetime(new Date());
    user.setUsername("username");
    
    userMapper.insert(user);

    return "Hello " + name;
  }
}
