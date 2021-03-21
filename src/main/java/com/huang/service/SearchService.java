package com.huang.service;

import com.alibaba.fastjson.JSON;
import com.huang.pojo.Blog;
import com.huang.pojo.Discuss;
import com.huang.pojo.Video;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


//    //放入ElasticSearch
//    public Boolean parseContent(String keywords) throws Exception {
//        List<content> contents = new HtmlParseUtil().parseJD(keywords);
//        BulkRequest bulkRequest = new BulkRequest();
//        bulkRequest.timeout("2m");
//        for (int i = 0; i < contents.size(); i++) {
//            bulkRequest.add(new IndexRequest("jd_goods")
//                    .source(JSON.toJSONString(contents.get(i)), XContentType.JSON));
//        }
//        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//        return bulk.hasFailures();
//    }

    //搜索
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize, String document) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("dream-fly-"+document);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }

    //搜索高亮
    public List<Map<String, Object>> searchPagehighlighter(String keyword, int pageNo, int pageSize, String document, String title2) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("dream-fly-"+document);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

//精准匹配
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery(title2, keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.field("title");

        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //System.out.println(searchResponse);
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //System.out.println(hit);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (title != null) {
                Text[] fragments = title.fragments();
                String new_title = "";
                for (Text text : fragments) {
                    new_title += text;
                }
                sourceAsMap.put("title", new_title);

            }
            list.add(sourceAsMap);

        }
        return list;
    }

    public void insertBlog(Blog blog) throws IOException {
        IndexRequest indexRequest = new IndexRequest("dream-fly-blog");
        indexRequest.source(JSON.toJSONString(blog), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
    public void insertDiscuss(Discuss discuss) throws IOException {
        IndexRequest indexRequest = new IndexRequest("dream-fly-discuss");
        indexRequest.source(JSON.toJSONString(discuss), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
    public void insertVideo(Video video) throws IOException {
        IndexRequest indexRequest = new IndexRequest("dream-fly-video");
        indexRequest.source(JSON.toJSONString(video), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }
}
