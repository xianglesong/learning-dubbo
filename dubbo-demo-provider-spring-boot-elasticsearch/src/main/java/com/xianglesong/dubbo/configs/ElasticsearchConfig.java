package com.xianglesong.dubbo.configs;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

@Component
public class ElasticsearchConfig {

  private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

  @Value("${spring.elasticsearch.clustername}")
  private String clusterName;

  @Value("${spring.elasticsearch.cluster-nodes}")
  private String clusterNodes;

  @PostConstruct
  void initialize() {
    logger.info("clusterName is {}", clusterName);
    logger.info("cluster-nodes is {}", clusterNodes);
  }

  @Bean
  public Client client() {
    // refresh client if config modify
    logger.info("elasticsearch client start.");
    Client client = getClientInstance();
    logger.info("elasticsearch client end.");
    return client;
  }

  private Client getClientInstance() {
    Client client = null;
    // Settings settings = Settings.builder().put("cluster.name",
    // clusterName).put("client.transport.sniff", true).build();
    Settings settings =
        Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
            .build();
    TransportClient transportClient = new PreBuiltTransportClient(settings);
    try {
      Integer index = 0;
      String[] nodes = clusterNodes.split(";");
      //
      InetSocketTransportAddress[] clusters = new InetSocketTransportAddress[nodes.length];
      for (String node : nodes) {
        InetSocketTransportAddress transportAddress =
            new InetSocketTransportAddress(InetAddress.getByName(node.split(":")[0]),
                Integer.parseInt(node.split(":")[1]));
        index++;
        transportClient.addTransportAddress(transportAddress);
      }
      // client = new PreBuiltTransportClient(settings).addTransportAddresses(clusters);
      client = transportClient;
    } catch (UnknownHostException e) {
      logger.error("Es Connection failed, application wont work as expected, FIX IT!!!!!!", e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("ES Exception", e);
      e.printStackTrace();
    }

    return client;
  }

}
