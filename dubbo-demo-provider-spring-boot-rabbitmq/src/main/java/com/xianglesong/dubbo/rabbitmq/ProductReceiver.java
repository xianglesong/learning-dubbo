package com.xianglesong.dubbo.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductReceiver {
  private static final Logger logger = LoggerFactory.getLogger(ProductReceiver.class);

  Boolean mqSwtich = true;

  @RabbitListener(queues = "${search.sync.index.rabbitmq}")
  // @RabbitListener(queues = "Mall_Push_ProductInfoChange")
  public void receive(Message message) {
    logger.info("receive: ");
    if (mqSwtich) {
      try {
        if (message != null && message.getBody() != null) {
          String jsonString = new String(message.getBody(), "UTF-8");
          logger.info("rec : " + jsonString);
        }
      } catch (Exception ex) {
        logger.error("mq process excepion", ex);
        throw new RuntimeException("mq process excepion");
      }
    } else {
      //
      while (true) {
        try {
          Thread.sleep(1000 * 10 * 60);
          // throw new RuntimeException("sleep");
        } catch (InterruptedException e) {
          e.printStackTrace();
          throw new RuntimeException("sleep");
        }
      }
    }
  }

}
