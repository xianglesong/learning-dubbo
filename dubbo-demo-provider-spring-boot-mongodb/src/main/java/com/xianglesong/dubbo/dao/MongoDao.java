package com.xianglesong.dubbo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;

import javax.annotation.Resource;

@Component
public class MongoDao {

  public static Logger logger = LoggerFactory.getLogger(MongoDao.class);

  @Resource(name = "serverMongoTemplate")
  public MongoTemplate mongoTemplate;

  public void execute() {
    // mongoTemplate.getDb().collectionExists("searchIndex");

    System.out.println(mongoTemplate.getDb().collectionExists("searchIndex"));
    System.out.println("execute: " + Calendar.getInstance());
  }
}
