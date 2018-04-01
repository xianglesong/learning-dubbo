package com.xianglesong.dubbo.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class SearchRedisConfig {
  @Value("${spring.redis.search.host}")
  private String host;

  @Value("${spring.redis.search.port}")
  private int port;

  @Value("${spring.redis.search.database}")
  private int database;

  @Value("${spring.redis.search.password}")
  private String password;

  @Value("${spring.redis.search.pool.max-idle}")
  private int maxIdle;

  @Value("${spring.redis.search.pool.max-wait}")
  private long maxWaitMillis;

  @Bean(name = "searchRedisConnectionFactory")
  public RedisConnectionFactory searchRedisConnectionFactory() {
    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
    redisConnectionFactory.setHostName(host);
    redisConnectionFactory.setPort(port);
    redisConnectionFactory.setDatabase(database);
    redisConnectionFactory.setPassword(password);

    JedisPoolConfig poolCofig = new JedisPoolConfig();
    poolCofig.setMaxIdle(maxIdle);
    poolCofig.setMaxWaitMillis(maxWaitMillis);
    // poolCofig.setTestOnBorrow(testOnBorrow);

    redisConnectionFactory.setPoolConfig(poolCofig);
    return redisConnectionFactory;
  }

  @Bean(name = "searchStringRedisTemplate")
  public StringRedisTemplate searchRedisTemplate(
      @Qualifier("searchRedisConnectionFactory") RedisConnectionFactory cf) {
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setConnectionFactory(cf);
    return stringRedisTemplate;
  }
}
