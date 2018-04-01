package com.xianglesong.dubbo.configs;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class BaseMongoConfig {

  @Value("${server.mongodb.hosts}")
  private String hosts;

  @Value("${server.mongodb.username}")
  private String username;

  @Value("${server.mongodb.password}")
  private String password;

  @Value("${server.mongodb.database}")
  private String database;

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getHosts() {
    return hosts;
  }

  public void setHosts(String hosts) {
    this.hosts = hosts;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public MongoTemplate mongoTemplate() throws Exception {
    MappingMongoConverter mongoConverter =
        new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()),
            new MongoMappingContext());
    mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); // typeKey 设置为 null。默认的无参构造为
                                                                    // _class
    return new MongoTemplate(mongoDbFactory(), mongoConverter);
  }

  private ServerAddress parseServerAddress(String host) throws UnknownHostException {
    ServerAddress addr = null;
    int mh = host.indexOf(':');
    if (mh < 0) {
      addr = new ServerAddress(host);
    } else {
      String serv = host.substring(0, mh).trim();
      int port = Integer.parseInt(host.substring(mh + 1).trim());
      addr = new ServerAddress(serv, port);
    }
    return addr;
  }

  public MongoClient mongo() throws Exception {
        List<MongoCredential> authList = new ArrayList<>();
        // 【mongodb 3.x】
        MongoCredential auth = MongoCredential.createScramSha1Credential(
                username, database, password.toCharArray());
        // 【mongodb 2.x】
        //    MongoCredential auth = MongoCredential.createMongoCRCredential(
        //            username, database, password.toCharArray());
        String[] hostArray = hosts.split("[,]");
        int countMongoServers = hostArray.length;
        while (countMongoServers-- > 0) {
            authList.add(auth);
        }
        MongoClient mongo = null;
        if (hostArray.length == 1) {
            ServerAddress addr = parseServerAddress(hosts);
            mongo = new MongoClient(addr, authList);
        } else {
            List<ServerAddress> reps = new ArrayList<>();
            for (String host : hostArray) {
                ServerAddress addr = parseServerAddress(host);
                reps.add(addr);
            }
            mongo = new MongoClient(reps, authList);
        }
        return mongo;
    }

  public MongoDbFactory mongoDbFactory() throws Exception {
    return new SimpleMongoDbFactory(mongo(), database);
  }

  public MongoConverter getMongoConverter() throws Exception {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
    MappingMongoConverter converter =
        new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
    MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
    converter.setTypeMapper(typeMapper);
    converter.afterPropertiesSet();
    return converter;
  }
}
