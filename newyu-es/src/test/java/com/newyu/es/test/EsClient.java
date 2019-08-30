package com.newyu.es.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * ClassName: EsClient <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-23 上午10:23 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class EsClient implements AutoCloseable {
    public static EsClient newInstance() {
        return newInstance("127.0.0.1", 9200);
    }

    public static EsClient newInstance(String hostname, int port) {
        return newInstance(new HttpHost(hostname, port, "http"));
    }

    public static EsClient newInstance(HttpHost httpHost) {
        return new EsClient(httpHost);
    }

    private RestHighLevelClient client;

    private EsClient(HttpHost httpHost) {
        client = new RestHighLevelClient(
                RestClient.builder(httpHost));
    }

    public RestHighLevelClient client() {
        return client;
    }

    @Override
    public void close() {
        log.debug("关闭es的链接");
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException("关闭es链接出现IO异常", e);
        } catch (Exception e) {
            throw new RuntimeException("关闭es链接出现异常", e);
        }
    }
}
