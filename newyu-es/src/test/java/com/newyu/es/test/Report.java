package com.newyu.es.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.newyu.es.test.bean.*;
import com.newyu.utils.excel.ExcelTableBuilder;
import com.newyu.utils.excel.Rank;
import com.newyu.utils.excel.Row;
import com.newyu.utils.excel.Table;
import com.newyu.utils.tool.ClazzNameToNumber;
import com.newyu.utils.tool.NumberFormatTools;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
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
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ClassName: Report <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-23 上午10:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Report {
    Map<String, int[]> uplineScoreMap = Maps.newHashMap();
    String[] keyNames = new String[]{"1.一本", "2.二本", "3.专科"};
    String[] cjFileds = new String[]{"gkyuwen", "gkshuxue", "gkyingyu", "gkzonghe", "gkzf"};
    String[] zkCjFileds = new String[]{"zkyuwen", "zkshuxue", "zkyingyu", "zkwuli", "zkhuaxue", "zkshengwu", "zkzhengzhi", "zklishi", "zkdili", "zkzonghe", "zkzf"};
    NumberFormatTools format = NumberFormatTools.newInstance();

    @Before
    public void before() throws Exception {
        uplineScoreMap = Maps.newHashMap();
        uplineScoreMap.put("2019.1", new int[]{535, 435, 200});
        uplineScoreMap.put("2019.2", new int[]{560, 480, 200});
        uplineScoreMap.put("2018.1", new int[]{530, 430, 200});
        uplineScoreMap.put("2018.2", new int[]{575, 490, 200});
        uplineScoreMap.put("2017.1", new int[]{500, 410, 300});
        uplineScoreMap.put("2017.2", new int[]{555, 465, 300});
    }


    @Test
    public void teacherInfoTest() throws Exception {
        List<School> schools = Lists.newArrayList();
        schools.add(School.builder().code("10").name("溪洛渡高中").build());
        schools.add(School.builder().code("20").name("永一中").build());

        List<Subject> subjects = Lists.newArrayList();
        subjects.add(Subject.builder().code("yuwen").name("语文").build());
        subjects.add(Subject.builder().code("shuxue").name("数学").build());
        subjects.add(Subject.builder().code("yingyu").name("英语").build());
        subjects.add(Subject.builder().code("wuli").name("物理").build());
        subjects.add(Subject.builder().code("huaxue").name("化学").build());
        subjects.add(Subject.builder().code("shengwu").name("生物").build());
        subjects.add(Subject.builder().code("zhengzhi").name("政治").build());
        subjects.add(Subject.builder().code("lishi").name("历史").build());
        subjects.add(Subject.builder().code("dili").name("地理").build());
        int[] years = new int[]{2017, 2018, 2019};

        Arrays.stream(years).forEach(year -> {
            schools.forEach(x -> {
                Table table = new Table();
                table.addHeader(new Row().add("科目", "教师", "班级"));
                subjects.forEach(y -> {
                    try {
                        teacherInfo(year, x, y, table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Path root = Paths.get("/home/liuyu/test/test-data/cj");
                ExcelTableBuilder.instance().builder(root.resolve(x.getName() + year + "=====教师任教班级信息表.xls").toString(), table);
            });
        });

    }

    public void teacherInfo(int year, School school, Subject subject, Table table) throws Exception {
        try (EsClient esClient = EsClient.newInstance()) {

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termQuery("schoolCode", school.getCode())));

            TermsAggregationBuilder termsAggBuilder = AggregationBuilders.terms("teacherName").field(subject.getCode() + ".grade3")
                    .subAggregation(AggregationBuilders.terms("clazzName").field("clazzName")
                            .subAggregation(AggregationBuilders.terms("wl").field("wl")));
            sourceBuilder.aggregation(termsAggBuilder);

            SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggs = searchResponse.getAggregations();


            List<Row> result = Lists.newArrayList();
            MultiBucketsAggregation teacherNameAggResult = aggs.get("teacherName");
            List<? extends MultiBucketsAggregation.Bucket> teacherNameBuckets = teacherNameAggResult.getBuckets();
            teacherNameBuckets.forEach(x -> {
                String teacherName = x.getKeyAsString();
                MultiBucketsAggregation clazzNameReslut = x.getAggregations().get("clazzName");
                List<? extends MultiBucketsAggregation.Bucket> clazzNameBuckets = clazzNameReslut.getBuckets();
                List<String> clazzNames = Lists.newArrayList();
                clazzNameBuckets.forEach(y -> {
                    String clazzName = y.getKeyAsString();
                    MultiBucketsAggregation wlReslut = y.getAggregations().get("wl");
                    List<? extends MultiBucketsAggregation.Bucket> wlBuckets = wlReslut.getBuckets();
                    String wl = wlBuckets.get(0).getKeyAsString();
                    String wlTag = wl.equals("1") ? "理科" : "文科";
                    clazzNames.add(clazzName + "(" + wlTag + ")");
                });

                clazzNames.sort((d1, d2) -> {
                    int v1 = ClazzNameToNumber.toNum(d1);
                    int v2 = ClazzNameToNumber.toNum(d2);
                    return Ints.compare(v1, v2);
                });

                result.add(new Row().add(subject.getName(), teacherName, String.join(",", clazzNames)));
            });

            result.get(0).getCellOfIndex(0).setMergeRow(result.size() - 1);
            table.addBody(result);
        }
    }

    public Map<String, String> teacherInfo2(int year, School school) throws Exception {
        List<Subject> subjects = Lists.newArrayList();
        subjects.add(Subject.builder().code("yuwen").name("语文").build());
        subjects.add(Subject.builder().code("shuxue").name("数学").build());
        subjects.add(Subject.builder().code("yingyu").name("英语").build());
        subjects.add(Subject.builder().code("wuli").name("物理").build());
        subjects.add(Subject.builder().code("huaxue").name("化学").build());
        subjects.add(Subject.builder().code("shengwu").name("生物").build());
        subjects.add(Subject.builder().code("zhengzhi").name("政治").build());
        subjects.add(Subject.builder().code("lishi").name("历史").build());
        subjects.add(Subject.builder().code("dili").name("地理").build());
        Map<String, String> clazzSubjectToTeacherMap = Maps.newHashMap();
        for (Subject subject : subjects) {
            try (EsClient esClient = EsClient.newInstance()) {
                SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
                sourceBuilder.size(0);
                sourceBuilder.query(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termQuery("schoolCode", school.getCode())));

                TermsAggregationBuilder termsAggBuilder = AggregationBuilders.terms("teacherName").field(subject.getCode() + ".grade3").size(100)
                        .subAggregation(AggregationBuilders.terms("clazzName").field("clazzName"));
                sourceBuilder.aggregation(termsAggBuilder);

                SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
                searchRequest.source(sourceBuilder);
                SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);

                Aggregations aggs = searchResponse.getAggregations();

                MultiBucketsAggregation teacherNameAggResult = aggs.get("teacherName");
                List<? extends MultiBucketsAggregation.Bucket> teacherNameBuckets = teacherNameAggResult.getBuckets();
                teacherNameBuckets.forEach(x -> {
                    String teacherName = x.getKeyAsString();
                    MultiBucketsAggregation clazzNameReslut = x.getAggregations().get("clazzName");
                    List<? extends MultiBucketsAggregation.Bucket> clazzNameBuckets = clazzNameReslut.getBuckets();
                    clazzNameBuckets.forEach(y -> {
                        String clazzName = y.getKeyAsString();
                        clazzSubjectToTeacherMap.put(subject.getName() + "." + clazzName, teacherName);
                    });
                });
            }
        }
        return clazzSubjectToTeacherMap;
    }


    @Test
    public void report() throws Exception {
        int[] years = new int[]{2017, 2018, 2019};
        int[] wls = new int[]{1, 2};
        List<School> schools = Lists.newArrayList();
        schools.add(School.builder().code("10").name("溪洛渡高中").build());
        schools.add(School.builder().code("20").name("永一中").build());

        schools.forEach(school -> {
            Arrays.stream(years).forEach(year -> {
                Arrays.stream(wls).forEach(wl -> {
                    try {
                        effectiveScoreTest(year, wl, school);
                        teacherUplineInFsd(year, wl, school);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        });

//        effectiveScoreTest(2017, 2, schools.get(0));
//        teacherUplineInFsd(2017, 2, schools.get(0));
    }


    public void effectiveScoreTest(int year, int wl, School school) throws Exception {

        Map<String, String> clazzSubjectToTeacherMap = teacherInfo2(year, school);
        List<EffectiveScore> effectiveScores = effectiveScore(year, wl, school);
        calcluateCoefficient(effectiveScores);
        SchoolAvg schoolAvg = clazzAvg(year, wl, school);


        List<String[]> subjectNameToFiledNames = Lists.newArrayList();
        subjectNameToFiledNames.add(new String[]{"总分", "zkzf", "gkzf"});
        subjectNameToFiledNames.add(new String[]{"语文", "zkyuwen", "gkyuwen"});
        subjectNameToFiledNames.add(new String[]{"数学", "zkshuxue", "gkshuxue"});
        subjectNameToFiledNames.add(new String[]{"英语", "zkyingyu", "gkyingyu"});
        subjectNameToFiledNames.add(new String[]{"综合", "zkzonghe", "gkzonghe"});


        Table table = new Table();
        Row header = new Row().add("科目,班级,任教老师,中考均分,中考均分排名,中考校均分,中考离均率,高考均分,高考均分排名,高考校均分".split(","));
        for (String keyName : keyNames) {
            header.add(keyName, keyName + "离均率", keyName + "增值", keyName + "增值排名");
        }
        table.addHeader(header);


        subjectNameToFiledNames.forEach(x -> {
            String subjectName = x[0];
            String zkfiled = x[1];
            String gkfiled = x[2];
            List<Row> rows = Lists.newArrayList();

            schoolAvg.getClazzAvgs().forEach(y -> {
                double zkavg = format.formatReturnDouble(y.getAvgMap().get(zkfiled));
                double gkavg = format.formatReturnDouble(y.getAvgMap().get(gkfiled));
                double zkSchoolAvg = format.formatReturnDouble(schoolAvg.getAvgMap().get(zkfiled));
                double gkSchoolAvg = format.formatReturnDouble(schoolAvg.getAvgMap().get(gkfiled));

                Row row = new Row();
                rows.add(row);
                row.add(subjectName);
                String clazzName = y.getName();
                row.add(clazzName);
                String teacherName = clazzSubjectToTeacherMap.get(subjectName + "." + clazzName);
                if (teacherName == null) {
                    teacherName = "";
                }
                row.add(teacherName);
                row.add(zkavg);
                row.add(Rank.builder().rankFiledName("中考均分").groupFiledName("科目").build());
                row.add(zkSchoolAvg);
                double zkljl = format.percentFormatNoSymbolDouble((zkavg - zkSchoolAvg) / zkSchoolAvg);
                row.add(zkljl);

                row.add(gkavg);
                row.add(Rank.builder().rankFiledName("高考均分").groupFiledName("科目").build());
                row.add(gkSchoolAvg);

                for (String keyName : keyNames) {
                    EffectiveScore effectiveScore = null;
                    for (EffectiveScore tmp : effectiveScores) {
                        if (keyName.equals(tmp.getName())) {
                            effectiveScore = tmp;
                            break;
                        }
                    }
                    double subjectEffectiveScores = format.formatReturnDouble(effectiveScore.getScore(gkfiled));
                    row.add(subjectEffectiveScores);
                    double gkljl = format.percentFormatNoSymbolDouble((gkavg - subjectEffectiveScores) / subjectEffectiveScores);
                    row.add(gkljl);
                    row.add(gkljl - zkljl);
                    row.add(Rank.builder().rankFiledName(keyName + "增值").groupFiledName("科目").build());
                }
            });

            rows.sort((o1, o2) -> {
                int v1 = ClazzNameToNumber.toNum(o1.getCellOfIndex(1).getValue().toString());
                int v2 = ClazzNameToNumber.toNum(o2.getCellOfIndex(1).getValue().toString());
                return Ints.compare(v1, v2);
            });
            rows.get(0).getCellOfIndex(0).setMergeRow(rows.size() - 1);
            table.addBody(rows);
        });
        Path root = Paths.get("/home/liuyu/test/test-data/cj");
        ExcelTableBuilder.instance().builder(root.resolve(school.getName() + year + (wl == 1 ? "理科" : "文科") + "班级有效分对比.xls").toString(), table);
        System.out.println();

    }

    private void calcluateCoefficient(List<EffectiveScore> effectiveScores) {
        effectiveScores.forEach(x -> {
            x.calcluateCoefficient("gkyuwen", "gkshuxue", "gkyingyu", "gkzonghe");
        });
    }

    public SchoolAvg clazzAvg(int year, int wl, School school) throws Exception {
        try (EsClient esClient = EsClient.newInstance()) {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("schoolCode", school.getCode()))
                            .must(QueryBuilders.termQuery("wl", wl))
                    ));

            for (int i = 0; i < cjFileds.length; i++) {
                sourceBuilder.aggregation(AggregationBuilders.avg(cjFileds[i] + "avg").field(cjFileds[i]));
            }
            for (int i = 0; i < zkCjFileds.length; i++) {
                sourceBuilder.aggregation(AggregationBuilders.avg(zkCjFileds[i] + "avg").field(zkCjFileds[i]));
            }

            TermsAggregationBuilder clazzAvgAggBuilder = AggregationBuilders.terms("clazzAvg").field("clazzName");
            sourceBuilder.aggregation(clazzAvgAggBuilder);
            for (int i = 0; i < cjFileds.length; i++) {
                clazzAvgAggBuilder.subAggregation(AggregationBuilders.avg(cjFileds[i] + "avg").field(cjFileds[i]));
            }
            for (int i = 0; i < zkCjFileds.length; i++) {
                clazzAvgAggBuilder.subAggregation(AggregationBuilders.avg(zkCjFileds[i] + "avg").field(zkCjFileds[i]));
            }
            SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);

            SchoolAvg schoolAvg = SchoolAvg.builder()
                    .count((int) searchResponse.getHits().totalHits)
                    .build();
            Aggregations aggs = searchResponse.getAggregations();
            for (int i = 0; i < cjFileds.length; i++) {
                Avg avg = aggs.get(cjFileds[i] + "avg");
                schoolAvg.getAvgMap().put(cjFileds[i], avg.getValue());
            }
            for (int i = 0; i < zkCjFileds.length; i++) {
                Avg avg = aggs.get(zkCjFileds[i] + "avg");
                schoolAvg.getAvgMap().put(zkCjFileds[i], avg.getValue());
            }

            MultiBucketsAggregation clazzsAvgAggResult = aggs.get("clazzAvg");
            List<? extends MultiBucketsAggregation.Bucket> clazzAvgBuckets = clazzsAvgAggResult.getBuckets();
            clazzAvgBuckets.forEach(x -> {
                ClazzAvg clazzAvg = ClazzAvg.builder()
                        .name(x.getKeyAsString())
                        .count((int) x.getDocCount())
                        .build();
                schoolAvg.getClazzAvgs().add(clazzAvg);

                for (int i = 0; i < cjFileds.length; i++) {
                    Avg avg = x.getAggregations().get(cjFileds[i] + "avg");
                    clazzAvg.getAvgMap().put(cjFileds[i], avg.getValue());
                }
                for (int i = 0; i < zkCjFileds.length; i++) {
                    Avg avg = x.getAggregations().get(zkCjFileds[i] + "avg");
                    clazzAvg.getAvgMap().put(zkCjFileds[i], avg.getValue());
                }
            });

            System.out.println();
            return schoolAvg;
        }
    }

    public List<EffectiveScore> effectiveScore(int year, int wl, School school) throws Exception {


        try (EsClient esClient = EsClient.newInstance()) {

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("schoolCode", school.getCode()))
                            .must(QueryBuilders.termQuery("wl", wl))
                    ));


            int[] uplineScores = uplineScoreMap.get(year + "." + wl);
            FiltersAggregator.KeyedFilter[] keyedFilters = new FiltersAggregator.KeyedFilter[keyNames.length];
            for (int i = 0; i < keyNames.length; i++) {
                keyedFilters[i] = new FiltersAggregator.KeyedFilter(keyNames[i], QueryBuilders.rangeQuery("gkzf").gte(uplineScores[i]));
            }
            FiltersAggregationBuilder uplineAggBuilder = AggregationBuilders.filters("upline", keyedFilters);
            sourceBuilder.aggregation(uplineAggBuilder);
            for (String filed : cjFileds) {
                uplineAggBuilder.subAggregation(AggregationBuilders.avg(filed + "avg").field(filed));
            }

            SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggs = searchResponse.getAggregations();
            MultiBucketsAggregation uplineAggResult = aggs.get("upline");
            List<? extends MultiBucketsAggregation.Bucket> uplineBuckets = uplineAggResult.getBuckets();

            List<EffectiveScore> result = Lists.newArrayList();
            for (int i = 0; i < uplineBuckets.size(); i++) {
                MultiBucketsAggregation.Bucket x = uplineBuckets.get(i);
                EffectiveScore effectiveScore = EffectiveScore.builder()
                        .uplineScore(uplineScores[i])
                        .name(x.getKeyAsString())
                        .count((int) x.getDocCount())
                        .build();
                Aggregations subjectAvgAggResult = x.getAggregations();
                Arrays.stream(cjFileds).forEach(y -> {
                    Avg avg = subjectAvgAggResult.get(y + "avg");
                    effectiveScore.getSubjectAvgMap().put(y, avg.getValue());
                });
                result.add(effectiveScore);
            }
            return result;
        }
    }


    public void teacherUplineInFsd(int year, int wl, School school) throws Exception {


        try (EsClient esClient = EsClient.newInstance()) {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("schoolCode", school.getCode()))
                            .must(QueryBuilders.termQuery("wl", wl))
                    ));


            HistogramAggregationBuilder fsdAgg = AggregationBuilders.histogram("fsd").field("zkzf").interval(50).order(BucketOrder.key(false));//.extendedBounds(0, 1000);
            sourceBuilder.aggregation(fsdAgg);
            RangeAggregationBuilder rangAgg = AggregationBuilders.range("cj_upline")
                    .field("gkzf");
            int[] uplineScores = uplineScoreMap.get(year + "." + wl);

            int preUplineScore = 751;
            for (int i = 0; i < keyNames.length; i++) {
                rangAgg.addRange(keyNames[i], uplineScores[i], preUplineScore);
                preUplineScore = uplineScores[i];
            }
            fsdAgg.subAggregation(rangAgg);
            List<FieldSortBuilder> sorts = Lists.newArrayList();
            sorts.add(new FieldSortBuilder("_key").order(SortOrder.ASC));
            rangAgg.subAggregation(PipelineAggregatorBuilders.bucketSort("mySort", sorts));

            SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);

            int totalNum = (int) searchResponse.getHits().totalHits;

            Aggregations aggs = searchResponse.getAggregations();

            List<Segment> mySegments = Lists.newArrayList();

            MultiBucketsAggregation uplineAggResult = aggs.get("fsd");
            List<? extends MultiBucketsAggregation.Bucket> fsdHistogramBucket = uplineAggResult.getBuckets();
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


            System.out.println();

            Table table = new Table();
            Row header = new Row().add("分数段", "人数");
            for (String keyName : keyNames) {
                header.add(keyName, keyName + "上线人数", keyName + "上线率");
            }
            table.addHeader(header);
            mySegments.forEach(segment -> {
                Row row = new Row();
                if (Double.isInfinite(segment.getTo())) {
                    row.add("大于等于" + segment.getFrom());
                } else {
                    row.add(segment.getFrom() + "-" + segment.getTo());
                }
                row.add(segment.getCount());

                for (String keyName : keyNames) {
                    Upline upline = null;
                    for (Upline tmp : segment.getUplines()) {
                        if (keyName.equals(tmp.getName())) {
                            upline = tmp;
                            break;
                        }
                    }
                    row.add(upline.getFrom());
                    row.add(upline.getCount());
                    row.add(format.percentFormatNoSymbol(upline.getCount() * 1.0 / totalNum));
                }
                table.addBody(row);
            });

            Path root = Paths.get("/home/liuyu/test/test-data/cj");
            ExcelTableBuilder.instance().builder(root.resolve(school.getName() + year + (wl == 1 ? "理科" : "文科") + "上线人数统计.xls").toString(), table);
            System.out.println();
        }

    }

    public void teacherUplineInFsd2(int year, int wl, School school) throws Exception {


        try (EsClient esClient = EsClient.newInstance()) {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
//            sourceBuilder.query(QueryBuilders.termQuery("wl", 1));
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("schoolCode", school.getCode()))
                            .must(QueryBuilders.termQuery("wl", wl))
                    ));

//            sourceBuilder.postFilter(QueryBuilders.termQuery("wl", 1));


            TermsAggregationBuilder teacherTermsAgg = AggregationBuilders.terms("teachers").field("teachers");
            sourceBuilder.aggregation(teacherTermsAgg);
            HistogramAggregationBuilder fsdAgg = AggregationBuilders.histogram("fsd").field("zkzf").interval(50).order(BucketOrder.key(false));//.extendedBounds(0, 1000);
            teacherTermsAgg.subAggregation(fsdAgg);
            RangeAggregationBuilder rangAgg = AggregationBuilders.range("cj_upline")
                    .field("gkzf");
            int[] uplineScores = uplineScoreMap.get(year + "." + wl);

            int preUplineScore = 751;
            for (int i = 0; i < keyNames.length; i++) {
                rangAgg.addRange(keyNames[i], uplineScores[i], preUplineScore);
                preUplineScore = uplineScores[i];
            }
            fsdAgg.subAggregation(rangAgg);
            List<FieldSortBuilder> sorts = Lists.newArrayList();
            sorts.add(new FieldSortBuilder("_key").order(SortOrder.ASC));
            rangAgg.subAggregation(PipelineAggregatorBuilders.bucketSort("mySort", sorts));

            SearchRequest searchRequest = new SearchRequest("liuyu_" + year);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = esClient.client().search(searchRequest, RequestOptions.DEFAULT);


            Aggregations aggs = searchResponse.getAggregations();
            Stat result = Stat.builder()
                    .count((int) searchResponse.getHits().totalHits)
                    .build();

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
                    Accumulator sumCoutn = Accumulator.builder().build();
                    uplines.forEach(z -> {
                        Row row = new Row();
                        row.add(x.getName());
                        if (Double.isInfinite(y.getTo())) {
                            row.add("大于等于" + y.getFrom());
                        } else {
                            row.add(y.getFrom() + "-" + y.getTo());
                        }
                        if (sumCoutn.increase() == 0) {
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

            Path root = Paths.get("/home/liuyu/test/test-data/cj");
            ExcelTableBuilder.instance().builder(root.resolve(school.getName() + year + (wl == 1 ? "理科" : "文科") + "上线人数统计.xls").toString(), table);
            System.out.println();
        }

    }
}
