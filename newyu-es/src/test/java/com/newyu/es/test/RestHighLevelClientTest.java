package com.newyu.es.test;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * ClassName: RestHighLevelClient <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-18 下午2:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class RestHighLevelClientTest {

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
    public void indexTest() throws Exception {
        Map<String, String> dataset = Maps.newHashMap();
        dataset.put("name", "name1");
        dataset.put("user", "user1");
        IndexRequest indexRequest = new IndexRequest("liuyu_test", "_doc", "1");
        indexRequest.source(dataset);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        RestStatus restStatus = indexResponse.status();
        DocWriteResponse.Result result = indexResponse.getResult();
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
        System.out.println();
    }


    @Test
    public void getRequest() throws Exception {
        GetRequest getRequest = new GetRequest("liuyu_test", "_doc", "1");
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);


        String index = getResponse.getIndex();
        String type = getResponse.getType();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            Map<String, DocumentField> fieldMap = getResponse.getFields();
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
        } else {
            System.out.println("没有数据");
        }
    }

    @Test
    public void updateRequest() throws Exception {
        UpdateRequest request = new UpdateRequest("liuyu_test", "_doc", "1");
        request.fetchSource(true);
        request.doc("name", "name update2");
        UpdateResponse updateResponse = client.update(
                request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();


        GetResult result = updateResponse.getGetResult();
        if (result.isExists()) {
            String sourceAsString = result.sourceAsString();
            Map<String, Object> sourceAsMap = result.sourceAsMap();
            byte[] sourceAsBytes = result.source();
        }
    }

    @Test
    public void bulkRequest() throws Exception {
        BulkRequest request = new BulkRequest("liuyu_test", "_doc");
        request.add(new IndexRequest().id("2").source(XContentType.JSON, "name", "name2", "user", "user2"));
        request.add(new IndexRequest().id("3").source(XContentType.JSON, "name", "name3", "user", "user3"));
        request.add(new UpdateRequest().id("1").doc("name", "name1"));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        if (bulkResponse.hasFailures()) {
            log.debug("执行有错误");
        }

        for (BulkItemResponse bulkItemResponse : bulkResponse) {

            DocWriteResponse itemResponse = bulkItemResponse.getResponse();

            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
        }
        log.debug("执行完毕");

    }


    @Test
    public void bulkProcessor() throws Exception {
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk [{}] with {} requests",
                        executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    log.debug("Bulk [{}] completed in {} milliseconds",
                            executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                log.error("Failed to execute bulk", failure);
            }
        };


        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) ->
                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener);
        builder.setGlobalIndex("liuyu_test");
        builder.setGlobalType("_doc");
        builder.setBulkActions(500);
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(0);
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        builder.setBackoffPolicy(BackoffPolicy
                .constantBackoff(TimeValue.timeValueSeconds(1L), 3));
        BulkProcessor bulkProcessor = builder.build();

        bulkProcessor.add(new IndexRequest().id("4").source(XContentType.JSON, "name", "name4", "user", "user4"));
        bulkProcessor.add(new IndexRequest().id("5").source(XContentType.JSON, "name", "name5", "user", "user5"));
        bulkProcessor.close();

        System.out.println();
    }

}
