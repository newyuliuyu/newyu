package com.newyu.es.test;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: LinkEsTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-18 上午11:04 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class LinkEsTest {

    private RestClient client;

    @Before
    public void before() throws Exception {
        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        RestClient.FailureListener failureListener = new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {

            }
        };


        client = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"))
//                .setDefaultHeaders(defaultHeaders)//设置默认的请求头
//                .setFailureListener(failureListener)//设置失败监听
//                .setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS)//设置节点选择器
//                .setRequestConfigCallback(
//                        new RestClientBuilder.RequestConfigCallback() {
//                            @Override
//                            public RequestConfig.Builder customizeRequestConfig(
//                                    RequestConfig.Builder requestConfigBuilder) {
//                                return requestConfigBuilder.setSocketTimeout(10000);
//                            }
//                        })//修改默认请求配置
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(
//                            HttpAsyncClientBuilder httpClientBuilder) {
//                        return httpClientBuilder.setProxy(
//                                new HttpHost("proxy", 9000, "http"));
//                    }
//                })//修改默认http链接的配置
                .build();
    }

    @After
    public void after() throws Exception {
        client.close();
    }

    @Test
    public void firstLInke() throws Exception {
        Request request = new Request("GET", "liuyu_fx/_search");
        request.setJsonEntity("{\n" +
                "  \"size\": 3, \n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}");
        Response response = client.performRequest(request);

        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());

    }

    @Test
    public void asyncTest() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Request request = new Request("GET", "liuyu_fx/_search");
        request.setJsonEntity("{\n" +
                "  \"size\": 3, \n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}");
        client.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                RequestLine requestLine = response.getRequestLine();
                HttpHost host = response.getHost();
                int statusCode = response.getStatusLine().getStatusCode();
                Header[] headers = response.getHeaders();
                try {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    System.out.println(responseBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("请求失败");
                latch.countDown();
            }
        });
        latch.await();
        System.out.println("运行完毕");
    }
}
