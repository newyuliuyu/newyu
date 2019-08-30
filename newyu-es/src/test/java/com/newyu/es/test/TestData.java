package com.newyu.es.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ClassName: TestData <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-22 上午10:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class TestData {
    private RestHighLevelClient client;

    @Before
    public void before() throws Exception {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                ));
    }

    @After
    public void after() throws Exception {
        client.close();
    }


    @Test
    public void testData1() throws Exception {
        BulkRequest request = new BulkRequest("liuyu_test1", "_doc");
        request.add(new IndexRequest().id("1").source(XContentType.JSON,"name","name1","cj",1));
        request.add(new IndexRequest().id("2").source(XContentType.JSON,"name","name2","cj",2));
        request.add(new IndexRequest().id("3").source(XContentType.JSON,"name","name3","cj",3));
        request.add(new IndexRequest().id("4").source(XContentType.JSON,"name","name4","cj",4));
        request.add(new IndexRequest().id("5").source(XContentType.JSON,"name","name5","cj",5));
        request.add(new IndexRequest().id("6").source(XContentType.JSON,"name","name6","cj",6));
        request.add(new IndexRequest().id("7").source(XContentType.JSON,"name","name7","cj",7));
        request.add(new IndexRequest().id("8").source(XContentType.JSON,"name","name8","cj",8));
        request.add(new IndexRequest().id("9").source(XContentType.JSON,"name","name9","cj",9));
        request.add(new IndexRequest().id("10").source(XContentType.JSON,"name","name10","cj",10));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
    }
    @Test
    public void testData2() throws Exception {
        BulkRequest request = new BulkRequest("liuyu_test2", "_doc");
        request.add(new IndexRequest().id("1").source(XContentType.JSON,"name","name1","cj",1));
        request.add(new IndexRequest().id("2").source(XContentType.JSON,"name","name2","cj",2));
        request.add(new IndexRequest().id("3").source(XContentType.JSON,"name","name3","cj",3));
        request.add(new IndexRequest().id("4").source(XContentType.JSON,"name","name4","cj",5));
        request.add(new IndexRequest().id("5").source(XContentType.JSON,"name","name5","cj",5));
        request.add(new IndexRequest().id("6").source(XContentType.JSON,"name","name6","cj",6));
        request.add(new IndexRequest().id("7").source(XContentType.JSON,"name","name7","cj",8));
        request.add(new IndexRequest().id("8").source(XContentType.JSON,"name","name8","cj",8));
        request.add(new IndexRequest().id("9").source(XContentType.JSON,"name","name9","cj",9));
        request.add(new IndexRequest().id("10").source(XContentType.JSON,"name","name10","cj",10));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
    }
}
