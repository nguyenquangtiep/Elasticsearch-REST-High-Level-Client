package com.mark;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        /**
         * Initialize the RestHighLevelClient
         */
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        /**
         * Index a document
         */
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("user", "kimchy");
//        jsonMap.put("postDate", new Date());
//        jsonMap.put("message", "trying out Elasticsearch");
//        IndexRequest indexRequest = new IndexRequest("myIndex").id("1").source(jsonMap);

        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2024-03-25\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexRequest request = new IndexRequest("test");
        request.id("1");
        request.source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println("Before delete");

        /**
         * Get a document
         */
        GetRequest getRequest = new GetRequest("test", "1");
        GetResponse getResponse = null;
        getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse);

        /**
         * Delete a document
         */

        DeleteRequest deleteRequest = new DeleteRequest("test", "1");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

        /**
         * Update a document
         */

//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("updated", new Date());
//        jsonMap.put("reason", "daily update");
//
//        UpdateRequest updateRequest = new UpdateRequest("test", "1").doc(jsonMap);
//        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println("After delete");
        GetRequest getRequest2 = new GetRequest("test", "1");
        GetResponse getResponse2 = null;
        getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse);

    }
}