package com.xianglesong.dubbo.configs;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


@Component
public class ElasticsearchUtils {

  private static final Logger logger = LoggerFactory.getLogger(ElasticsearchUtils.class);

  @Autowired
  Client client;

  public boolean indexExist(String indexName) {
    IndicesExistsResponse indicesExistsResponse =
        client.admin().indices().exists(new IndicesExistsRequest(new String[] {indexName}))
            .actionGet();

    if (indicesExistsResponse.isExists()) {
      return true;
    }

    logger.info("index  " + indexName + "  not exist");
    return false;
  }

  public boolean createIndex(String indexName) {
    if (!indexExist(indexName)) {
      client.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();
      logger.info("create index " + indexName + " success");
      return true;
    }
    return false;
  }

  public boolean deleteIndex(String indexName) {
    if (indexExist(indexName)) {
      client.admin().indices().delete(new DeleteIndexRequest(indexName)).actionGet();
      logger.info("delete index " + indexName + " success");
      return true;
    }
    return false;
  }

  public String addIndex(String index, String type, String id, String json) {
    logger.info("add index: " + index + " type:" + type + " json:" + json);

    if (id == null || id.equals("")) {
      id = UUID.randomUUID().toString().replace("-", "");
    }

    IndexResponse response =
        client.prepareIndex(index, type, id).setSource(json).execute().actionGet();

    String _id = response.getId();

    return _id;
  }

  public void deleteIndex(String index, String type, String id) {
    logger.info("delete index: " + id);
    client.prepareDelete(index, type, id).execute().actionGet();
  }

  public GetResponse searchIndexById(String index, String type, String id) {
    GetResponse response = client.prepareGet(index, type, id).execute().actionGet();

    return response;
  }

  public boolean typeExist(String indexName, String type) {
    TypesExistsResponse response =
        client.admin().indices().prepareTypesExists(indexName).setTypes(type).execute().actionGet();

    return response.isExists();
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void createType(String indexName, String type) {
    if (!typeExist(indexName, type)) {
      XContentBuilder mapping = null;
      try {
        mapping =
            jsonBuilder()
                .startObject()
                // 索引里面的字段
                .startObject("properties").startObject("id").field("type", "long")
                .field("store", "yes").field("index", "not_analyzed")
                .field("include_in_all", "false").endObject().startObject("thumb")
                .field("type", "string").field("store", "yes").field("index", "not_analyzed")
                .field("include_in_all", "false").endObject().endObject().endObject();


        PutMappingRequest putMappingRequest =
            Requests.putMappingRequest(indexName).type(type).source(mapping);
        client.admin().indices().putMapping(putMappingRequest).actionGet();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
