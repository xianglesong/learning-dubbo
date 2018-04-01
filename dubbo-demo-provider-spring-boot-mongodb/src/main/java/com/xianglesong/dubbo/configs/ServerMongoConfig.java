package com.xianglesong.dubbo.configs;

import com.mongodb.MongoClient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "server.mongodb")
public class ServerMongoConfig extends BaseMongoConfig {

  @Override
  @Primary
  @Bean(name = "serverMongoTemplate")
  public MongoTemplate mongoTemplate() throws Exception {
    return super.mongoTemplate();
  }

  @Override
  @Primary
  @Bean(name = "serverMongo")
  public MongoClient mongo() throws Exception {
    return super.mongo();
  }
}
