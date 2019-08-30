package com.newyu.es.test;

import com.google.common.collect.Lists;
import com.newyu.es.test.bean.Segment;
import com.newyu.es.test.bean.Stat;
import com.newyu.es.test.bean.Term;
import com.newyu.es.test.bean.Upline;
import com.newyu.utils.excel.ExcelTableBuilder;
import com.newyu.utils.excel.Row;
import com.newyu.utils.excel.Table;
import com.newyu.utils.tool.NumberFormatTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SearchRequestTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-19 下午1:47 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class SearchRequestTest {
    private RestHighLevelClient client;
    private NumberFormatTools format = new NumberFormatTools();

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
    public void first() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest("liuyu_test");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

//        获取状态信息
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();
//        获取当前查询关于分片的信息
        int totalShards = searchResponse.getTotalShards();
        int successfulShards = searchResponse.getSuccessfulShards();
        int failedShards = searchResponse.getFailedShards();

//        获取第一层hits的信息
        SearchHits hits = searchResponse.getHits();
        long numHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String id = hit.getId();
            float score = hit.getScore();

            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            System.out.println();
        }
    }

    @Test
    public void aggs() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);

//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
//                .field("company.keyword");
//        aggregation.subAggregation(AggregationBuilders.avg("average_age")
//                .field("age"));
        searchSourceBuilder.aggregation(AggregationBuilders.avg("zkzf_avg")
                .field("gkzf"));
        searchSourceBuilder.aggregation(AggregationBuilders.count("mycount").field("gkzf"));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("clazzName").field("clazzName"));


        SearchRequest searchRequest = new SearchRequest("liuyu_fx");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggs = searchResponse.getAggregations();


        Avg avg = aggs.get("zkzf_avg");

        System.out.println(avg.getValue());


        Terms byClazzNme = aggs.get("clazzName");
        Terms.Bucket elasticBucket = byClazzNme.getBucketByKey("理1班");
        log.debug("key[{}]={}", elasticBucket.getKeyAsString(), elasticBucket.getDocCount());

        System.out.println();


    }

    @Test
    public void aggs2() throws Exception {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(0);

        sourceBuilder.query(QueryBuilders.termQuery("wl", 1));

        HistogramAggregationBuilder histogramAggs = AggregationBuilders.histogram("cj_group")
                .interval(50)
                .field("zkzf")
                .keyed(true)
                .order(BucketOrder.key(false))
                .extendedBounds(0, 1000);


        sourceBuilder.aggregation(histogramAggs);


        SearchRequest searchRequest = new SearchRequest("liuyu_fx");
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        int totalNum = ((Long) searchResponse.getHits().totalHits).intValue();

        Aggregations aggs = searchResponse.getAggregations();

        Histogram histogram = aggs.get("cj_group");


        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
        Accumulator sumCoutn = new Accumulator();
        buckets.forEach(x -> {
            double key = (Double) x.getKey();
            int count = ((Long) x.getDocCount()).intValue();
            sumCoutn.count = sumCoutn.count + count;
            log.debug("分数段{}-{},人数:{}/{},累加人数:{}/{}", key, key + 50,
                    count, format.percentFormat(count * 1.0 / totalNum),
                    sumCoutn.count, format.percentFormat(sumCoutn.count * 1.0 / totalNum));
        });
        System.out.println();

    }

    class Accumulator {
        int count = 0;
    }

    @Test
    public void aggsRang() throws Exception {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(0);

        sourceBuilder.query(QueryBuilders.termQuery("wl", 1));

        RangeAggregationBuilder rangAgg = AggregationBuilders.range("cj_upline")
                .field("gkzf")
                .addRange("1.一本", 535, 751)
                .addRange("2.二本", 435, 535)
                .addRange("3.三本", 200, 435);


        List<FieldSortBuilder> sorts = Lists.newArrayList();
        sorts.add(new FieldSortBuilder("_key").order(SortOrder.ASC));

        rangAgg.subAggregation(PipelineAggregatorBuilders.bucketSort("mySort", sorts));

        sourceBuilder.aggregation(rangAgg);


        SearchRequest searchRequest = new SearchRequest("liuyu_fx");
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        int totalNum = ((Long) searchResponse.getHits().totalHits).intValue();

        Aggregations aggs = searchResponse.getAggregations();
        Range range = aggs.get("cj_upline");
        List<? extends Range.Bucket> rangBuckets = range.getBuckets();
        Accumulator sumCoutn = new Accumulator();
        rangBuckets.forEach(x -> {
            String key = x.getKeyAsString();
            int count = ((Long) x.getDocCount()).intValue();
            sumCoutn.count += count;

            log.debug("{}:人数：{}比例:{},累计人数{},累计比例{}",
                    x.getKeyAsString(),
                    count, format.percentFormat(count * 1.0 / totalNum),
                    sumCoutn.count, format.percentFormat(sumCoutn.count * 1.0 / totalNum));

        });

        System.out.println();

    }


    @Test
    public void teacherSegment() throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(0);
        sourceBuilder.query(QueryBuilders.termQuery("wl", 1));

        TermsAggregationBuilder teacherTermsAgg = AggregationBuilders.terms("teachers").field("teachers");
        sourceBuilder.aggregation(teacherTermsAgg);
        HistogramAggregationBuilder fsdAgg = AggregationBuilders.histogram("fsd").field("zkzf").interval(50).order(BucketOrder.key(false));//.extendedBounds(0, 1000);
        teacherTermsAgg.subAggregation(fsdAgg);
        RangeAggregationBuilder rangAgg = AggregationBuilders.range("cj_upline")
                .field("gkzf")
                .addUnboundedFrom("1.一本", 535)
                .addRange("2.二本", 435, 535)
                .addRange("3.三本", 200, 435);
        fsdAgg.subAggregation(rangAgg);

        List<FieldSortBuilder> sorts = Lists.newArrayList();
        sorts.add(new FieldSortBuilder("_key").order(SortOrder.ASC));
        rangAgg.subAggregation(PipelineAggregatorBuilders.bucketSort("mySort", sorts));


        SearchRequest searchRequest = new SearchRequest("liuyu_2017");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        Stat result = Stat.builder()
                .count((int) searchResponse.getHits().totalHits)
                .build();

        Aggregations aggs = searchResponse.getAggregations();


        List<Term> teachersTermsGroup = Lists.newArrayList();
        result.setTerms(teachersTermsGroup);

        Terms teacherTerms = aggs.get("teachers");
        List<? extends Terms.Bucket> teacherTermsBuckets = teacherTerms.getBuckets();
        teacherTermsBuckets.forEach(x -> {
            Term term = Term.builder()
                    .name(x.getKeyAsString())
                    .count((int) x.getDocCount())
                    .build();
            teachersTermsGroup.add(term);

            List<Segment> mySegments = Lists.newArrayList();
            term.setSegments(mySegments);
            Histogram fsdHistogram = x.getAggregations().get("fsd");
            List<? extends Histogram.Bucket> fsdHistogramBucket = fsdHistogram.getBuckets();
            fsdHistogramBucket.forEach(y -> {
                double from = (double) y.getKey();
                Segment segment = Segment.builder()
                        .from(from)
                        .to(from + 50)
                        .count((int) y.getDocCount())
                        .build();
                mySegments.add(segment);

                List<Upline> myUplines = Lists.newArrayList();
                segment.setUplines(myUplines);
                Range range = y.getAggregations().get("cj_upline");
                List<? extends Range.Bucket> cjUplieBucket = range.getBuckets();
                cjUplieBucket.forEach(z -> {
                    Upline upline = Upline.builder()
                            .name(z.getKeyAsString())
                            .from((double) z.getFrom())
                            .to((double) z.getTo())
                            .count((int) z.getDocCount())
                            .build();
                    myUplines.add(upline);
                });
            });

        });


        Table table = new Table();
        table.addHeader(new Row().add("老师", "分数段", "人数", "上线名称", "分数区间", "人数"));

        result.getTerms().forEach(x -> {
            List<Row> rows = Lists.newArrayList();
            List<Segment> segments = x.getSegments();
            segments.forEach(y -> {
                List<Upline> uplines = y.getUplines();
                Accumulator sumCoutn = new Accumulator();
                uplines.forEach(z -> {
                    Row row = new Row();
                    row.add(x.getName());
                    if (Double.isInfinite(y.getTo())) {
                        row.add("大于等于" + y.getFrom());
                    } else {
                        row.add(y.getFrom() + "-" + y.getTo());
                    }
                    if (sumCoutn.count++ == 0) {
                        row.add(y.getCount());
                    } else {
                        row.add(0);
                    }

                    row.add(z.getName());
                    row.add(z.getFrom() + "-" + z.getTo());
                    row.add(z.getCount());
                    rows.add(row);
                });
                Row row = rows.get(rows.size() - uplines.size());
                row.getCellOfIndex(1).setMergeRow(uplines.size() - 1);
                row.getCellOfIndex(2).setMergeRow(uplines.size() - 1);
            });
            rows.get(0).getCellOfIndex(0).setMergeRow(rows.size() - 1);
            table.addBody(rows);
        });

        Path root = Paths.get("/home/liuyu/test/test-data/cj/10");
        ExcelTableBuilder.instance().builder(root.resolve("a.xls").toString(), table);


        System.out.println();

    }


}
