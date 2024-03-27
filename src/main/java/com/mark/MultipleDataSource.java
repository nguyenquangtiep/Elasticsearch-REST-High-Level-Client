package com.mark;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class MultipleDataSource {

    private static final String ELASTICSEARCH_INDEX = "multiple_datasource_index";

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todos?" + "user=root&password=admin");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todos");

        while(resultSet.next()) {
            IndexRequest indexRequest = new IndexRequest(ELASTICSEARCH_INDEX);
            indexRequest.id(String.valueOf(resultSet.getInt("id")));
            indexRequest.source("name", resultSet.getString("name"), "source", "SQL");
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("Document indexed from SQL source: " + indexResponse);
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\SpringApplications\\CRUD with Elasticsearch REST High Client\\src\\main\\java\\com\\mark\\data.txt"));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            IndexRequest indexRequest = new IndexRequest(ELASTICSEARCH_INDEX);
            indexRequest.source("content", line, "source", "text file");
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("Document indexed from text file source: " + indexResponse);
        }

    }

}
